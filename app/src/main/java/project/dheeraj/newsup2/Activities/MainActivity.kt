package project.dheeraj.newsup2.Activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import project.dheeraj.newsup2.Adapters.PreferencesViewPagerAdapter
import project.dheeraj.newsup2.Adapters.SuggestedTopicsRecyclerViewAdapter
import project.dheeraj.newsup2.Adapters.TopStoriesHomeRecyclerViewAdapter
import project.dheeraj.newsup2.Model.NewsHeadlines
import project.dheeraj.newsup2.Model.SuggestedTopics
import project.dheeraj.newsup2.R
import project.dheeraj.newsup2.Retrofit.ApiInterface
import project.dheeraj.newsup2.Retrofit.RetrofitClient
import project.dheeraj.newsup2.Util.UtilMethods
import project.dheeraj.newsup2.ViewModel.TopStoriesViewModel
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var topStories: TextView
    lateinit var topStoriesRecyclerView: RecyclerView
    lateinit var suggestedTopicsRecyclerView: RecyclerView
    lateinit var preferencesViewPager: ViewPager
    lateinit var skeleton: Skeleton
    lateinit var homeSwipeRefreshLayout: SwipeRefreshLayout
    lateinit var firebaseAnalytics: FirebaseAnalytics
    lateinit var welcomeText: TextView
    lateinit var layoutMain: View
    lateinit var dialogNoInternet: View
    lateinit var buttonTryAgain: Button
    lateinit var apiInterface: ApiInterface
    lateinit var viewModel: TopStoriesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        viewModel = ViewModelProviders.of(this).get(TopStoriesViewModel::class.java)

        topStories = findViewById(R.id.view_all_top_stories)
        welcomeText = findViewById(R.id.welcomeTextView)
        layoutMain = findViewById(R.id.layout_main)
        dialogNoInternet = findViewById(R.id.dialog_no_internet)
        buttonTryAgain = findViewById(R.id.button_try_again)
        preferencesViewPager = findViewById(R.id.home_view_pager)
        apiInterface = RetrofitClient.getClient().create(ApiInterface::class.java)

        topStoriesRecyclerView =
            findViewById(R.id.top_stories_recycler_view)
        suggestedTopicsRecyclerView =
            findViewById(R.id.suggested_topics_recycler_view)
        homeSwipeRefreshLayout = findViewById(R.id.home_swipe_refresh)

        skeleton = topStoriesRecyclerView.applySkeleton(
            R.layout.shimmer_round_top_headlines,
            5
        )
        skeleton.maskCornerRadius = 480F
        skeleton.shimmerDurationInMillis = 1500
        skeleton.showSkeleton()

        val screenWidth = getScreenWidth()
        preferencesViewPager.clipToPadding = false
        preferencesViewPager.setPadding(0,0,220,0)

        val gifImage = findViewById<ImageView>(R.id.no_internet_image)

        Glide.with(this)
            .load(R.drawable.no_internet_gif_2)
            .into(gifImage)

        topStories.setOnClickListener {
            intent = Intent(this, TopStoriesActivity::class.java)
            intent.putExtra("name", "Top Headlines")
            startActivity(intent)
        }

        buttonTryAgain.setOnClickListener {
            getInternetState()
            getCurrentTime()
            observeNews()
        }

        getInternetState()
        getCurrentTime()
        getTopics()
//        getNews()
        observeNews()

        viewModel.getTopHeadlines()
        viewModel.getArticles()

        homeSwipeRefreshLayout.setColorSchemeResources(
            android.R.color.holo_blue_dark,
            android.R.color.holo_red_dark,
            android.R.color.holo_purple,
            android.R.color.holo_green_dark
        )

        homeSwipeRefreshLayout.setOnRefreshListener {
            getCurrentTime()
            getInternetState()
            observeNews()
        }


    }

    private fun getTopics() {

        var suggestedTopics = mutableListOf<SuggestedTopics>()

        suggestedTopics.add(
            SuggestedTopics(
                R.drawable.ic_undraw_finance_0bdk,
                "Business"
            )
        )
        suggestedTopics.add(            SuggestedTopics(
                R.drawable.ic_undraw_compose_music_ovo2,
                "Entertainment"
            )
        )
        suggestedTopics.add(SuggestedTopics(R.drawable.ic_undraw_game_day_ucx9, "Sports"))
        suggestedTopics.add(SuggestedTopics(R.drawable.ic_undraw_science_fqhl, "Science"))
        suggestedTopics.add(SuggestedTopics(R.drawable.ic_undraw_virtual_reality_y5ig, "Technology"))
        suggestedTopics.add(SuggestedTopics(R.drawable.ic_undraw_medicine_b1ol, "Medical"))
        suggestedTopics.add(
            SuggestedTopics(
                R.drawable.ic_undraw_travel_together_re_kjf2,
                "International"
            )
        )

        suggestedTopicsRecyclerView.layoutManager = GridLayoutManager(applicationContext, 3)
        suggestedTopicsRecyclerView.adapter =
            SuggestedTopicsRecyclerViewAdapter(applicationContext, suggestedTopics)
    }

    override fun onRestart() {
        super.onRestart()
        getInternetState()
        getCurrentTime()
    }

    override fun onResume() {
        getInternetState()
        getCurrentTime()
        super.onResume()
    }

    override fun onBackPressed() {
        if (layoutMain.visibility == View.VISIBLE) {
            val snackbar =
                Snackbar.make(layoutMain, "Are you sure you want to exit?", Snackbar.LENGTH_SHORT)
                    .setAction("Yes") {
                        finishAffinity()
                    }
                    .setActionTextColor(Color.RED)
            snackbar.show()
        } else
            finish()
    }

    private fun getCurrentTime() {

        val dateFormatter = SimpleDateFormat("hh a")
        dateFormatter.isLenient = false
        val today = Date()
        val s = dateFormatter.format(today)

        val time = s.subSequence(0, 2).toString().toInt()
        val timeDuration = s.subSequence(3, 5).toString()

        if ((time < 4 || time == 12) && (timeDuration.equals("PM") || timeDuration.equals("pm"))) {
            welcomeText.text = "Good Afternoon!"
        } else if (time in 5..8 && (timeDuration == "PM" || timeDuration.equals("pm"))) {
            welcomeText.text = "Good Evening!"
        } else if (time in 9..11 && (timeDuration == "PM" || timeDuration.equals("pm"))) {
            welcomeText.text = "Welcome!"
        } else if ((time < 4 || time == 12) && (timeDuration.equals("AM") || timeDuration.equals("am"))) {
            welcomeText.text = "Welcome!"
        } else if (time in 5..11 && (timeDuration.equals("AM") || timeDuration.equals("am"))) {
            welcomeText.text = "Good Morning!"
        }

    }

    private fun getInternetState() {
        if (UtilMethods.isInternetAvailable(applicationContext)) {
            layoutMain.visibility = View.VISIBLE
            dialogNoInternet.visibility = View.GONE
        } else {
            layoutMain.visibility = View.GONE
            dialogNoInternet.visibility = View.VISIBLE
        }
    }

    private fun getScreenWidth(): Int{

        val display = getWindowManager().getDefaultDisplay();
        val size = Point();
        display.getSize(size);
        val width = size.x;
        val height = size.y;
        Log.e("Width", "" + width);
        Log.e("height", "" + height);

        return (width/3)*10
    }

    private fun observeNews() {

        viewModel.topliveData.observe(this, androidx.lifecycle.Observer {
            var myNewsList = mutableListOf<NewsHeadlines>()

                    for (i in it.articles) {

                        if (i.urlToImage != null) {

                            myNewsList.add(
                                NewsHeadlines(
                                    i.author,
                                    i.source.id,
                                    i.source.name,
                                    i.title,
                                    i.description,
                                    i.url,
                                    i.urlToImage,
                                    i.publishedAt,
                                    i.content
                                )
                            )
                        }

                        homeSwipeRefreshLayout.isRefreshing = false

            }

            topStoriesRecyclerView.adapter =
                TopStoriesHomeRecyclerViewAdapter(applicationContext, myNewsList)

            Log.d("List Items: ", myNewsList.size.toString())

        })

        viewModel.liveData.observe(this, androidx.lifecycle.Observer {
            var prefList = mutableListOf<NewsHeadlines>()

            for (i in it.articles) {

                if (i.urlToImage != null) {

                    prefList.add(
                        NewsHeadlines(
                            i.author,
                            i.source.id,
                            i.source.name,
                            i.title,
                            i.description,
                            i.url,
                            i.urlToImage,
                            i.publishedAt,
                            i.content
                        )
                    )
                }

                homeSwipeRefreshLayout.isRefreshing = false

            }

            preferencesViewPager.adapter =
                PreferencesViewPagerAdapter(applicationContext, prefList)

            Log.d("List Items: ", prefList.size.toString())

        })

    }

}
