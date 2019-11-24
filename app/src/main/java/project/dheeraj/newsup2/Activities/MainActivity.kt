package project.dheeraj.newsup2.Activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.google.firebase.analytics.FirebaseAnalytics
import project.dheeraj.newsup2.Adapters.SuggestedTopicsRecyclerViewAdapter
import project.dheeraj.newsup2.Adapters.TopStoriesHomeRecyclerViewAdapter
import project.dheeraj.newsup2.Model.NewsHeadlines
import project.dheeraj.newsup2.Model.SuggestedTopics
import project.dheeraj.newsup2.R
import project.dheeraj.newsup2.Retrofit.ApiInterface
import project.dheeraj.newsup2.Retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import project.dheeraj.newsup2.Model.ArticlesModel as ArticlesModel1


class MainActivity : AppCompatActivity() {

    lateinit var topStories: TextView
    lateinit var topStoriesRecyclerView : RecyclerView
    lateinit var suggestedTopicsRecyclerView : RecyclerView
    lateinit var skeleton: Skeleton
    lateinit var homeSwipeRefreshLayout: SwipeRefreshLayout
    lateinit var firebaseAnalytics: FirebaseAnalytics
    lateinit var welcomeText : TextView
    lateinit var layoutMain : View
    lateinit var dialogNoInternet : View
    lateinit var buttonTryAgain : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(project.dheeraj.newsup2.R.layout.activity_main)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val conMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


        topStories = findViewById(project.dheeraj.newsup2.R.id.view_all_top_stories)
        welcomeText = findViewById(project.dheeraj.newsup2.R.id.welcomeTextView)
        layoutMain = findViewById(R.id.layout_main)
        dialogNoInternet = findViewById(R.id.dialog_no_internet)
        buttonTryAgain = findViewById(R.id.button_try_again)

        topStoriesRecyclerView = findViewById(project.dheeraj.newsup2.R.id.top_stories_recycler_view)
        suggestedTopicsRecyclerView = findViewById(project.dheeraj.newsup2.R.id.suggested_topics_recycler_view)
        homeSwipeRefreshLayout = findViewById(project.dheeraj.newsup2.R.id.home_swipe_refresh)

        skeleton = topStoriesRecyclerView.applySkeleton(project.dheeraj.newsup2.R.layout.shimmer_round_top_headlines, 5)
        skeleton.maskCornerRadius = 480F
        skeleton.shimmerDurationInMillis = 1500
        skeleton.showSkeleton()

        val gifImage = findViewById<ImageView>(project.dheeraj.newsup2.R.id.no_internet_image)

        Glide.with(this)
            .load(project.dheeraj.newsup2.R.drawable.no_internet_gif_2)
            .into(gifImage)

        topStories.setOnClickListener {
            intent  = Intent(this, TopStoriesActivity::class.java)
            startActivity(intent)
        }

        buttonTryAgain.setOnClickListener {
            getInternetState()
            getCurrentTime()
            getNews()
        }


        getInternetState()
        getCurrentTime()
        getTopics()
        getNews()

        homeSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_dark,
            android.R.color.holo_red_dark,
            android.R.color.holo_purple,
            android.R.color.holo_green_dark)

        homeSwipeRefreshLayout.setOnRefreshListener {
            getCurrentTime()
            getInternetState()
            getNews()
        }


    }

    private fun getTopics() {

        var suggestedTopics = mutableListOf<SuggestedTopics>()

        suggestedTopics.add(SuggestedTopics(project.dheeraj.newsup2.R.drawable.business, "Business"))
        suggestedTopics.add(SuggestedTopics(project.dheeraj.newsup2.R.drawable.entertainment, "Entertainment"))
        suggestedTopics.add(SuggestedTopics(project.dheeraj.newsup2.R.drawable.sports, "Sports"))
        suggestedTopics.add(SuggestedTopics(project.dheeraj.newsup2.R.drawable.science, "Science"))
        suggestedTopics.add(SuggestedTopics(project.dheeraj.newsup2.R.drawable.technology, "Technology"))
        suggestedTopics.add(SuggestedTopics(project.dheeraj.newsup2.R.drawable.medical, "Medical"))
        suggestedTopics.add(SuggestedTopics(project.dheeraj.newsup2.R.drawable.international2, "International"))

        suggestedTopicsRecyclerView.layoutManager = GridLayoutManager(applicationContext, 3)
        suggestedTopicsRecyclerView.adapter = SuggestedTopicsRecyclerViewAdapter(applicationContext, suggestedTopics)
    }

    private fun getNews() {

        val apiInterface: ApiInterface = RetrofitClient.getClient().create(
            ApiInterface::class.java
        )

        val call: Call<ArticlesModel1> = apiInterface.getArticlesModel()
        call.enqueue(object : Callback<project.dheeraj.newsup2.Model.ArticlesModel> {
            override fun onFailure(
                call: Call<project.dheeraj.newsup2.Model.ArticlesModel>?,
                t: Throwable?
            ) {
                Toast.makeText(applicationContext, "Error Loading Data",Toast.LENGTH_SHORT).show()
                Log.e("Error", t?.message.toString())
                homeSwipeRefreshLayout.isRefreshing = false
            }

            override fun onResponse(
                call: Call<project.dheeraj.newsup2.Model.ArticlesModel>?,
                response: Response<project.dheeraj.newsup2.Model.ArticlesModel>?
            ) {

                var myNewsList = mutableListOf<NewsHeadlines>()

                if(response != null){
                    if(response.body() != null){
                        for(i in 0 until response.body()!!.totalResults){

                            if ( response.body()!!.articles.get(i).urlToImage != null ){

                                myNewsList.add(
                                    NewsHeadlines(
                                        response.body()!!.articles.get(i).author,
                                        response.body()!!.articles.get(i).source.id,
                                        response.body()!!.articles.get(i).source.name,
                                        response.body()!!.articles.get(i).title,
                                        response.body()!!.articles.get(i).description,
                                        response.body()!!.articles.get(i).url,
                                        response.body()!!.articles.get(i).urlToImage,
                                        response.body()!!.articles.get(i).publishedAt,
                                        response.body()!!.articles.get(i).content
                                    )
                                )

                                Log.d(
                                    "Data $i",
                                    (response.body()!!.articles.get(i).author +
                                            response.body()!!.articles.get(i).title +
                                            response.body()!!.articles.get(i).description +
                                            response.body()!!.articles.get(i).url +
                                            response.body()!!.articles.get(i).urlToImage +
                                            response.body()!!.articles.get(i).publishedAt +
                                            response.body()!!.articles.get(i).content)
                                )
                            }

                            homeSwipeRefreshLayout.isRefreshing = false

                        }
                    }
                }

                topStoriesRecyclerView.adapter = TopStoriesHomeRecyclerViewAdapter(applicationContext, myNewsList)

                Log.d("Total Results: ", response?.body()?.totalResults.toString())
                Log.d("List Items: ", myNewsList.size.toString())

            }

        })
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

    fun getCurrentTime(){

        val dateFormatter = SimpleDateFormat("hh a")
        dateFormatter.setLenient(false)
        val today = Date()
        val s = dateFormatter.format(today)
//        Toast.makeText(this, s.subSequence(0,2), Toast.LENGTH_SHORT).show()
//        Toast.makeText(this, s.subSequence(3,5), Toast.LENGTH_SHORT).show()

        val time = s.subSequence(0,2).toString().toInt()
        val timeDuration = s.subSequence(3, 5).toString()

        if ((time <4 || time == 12) && timeDuration.equals("PM")){
//            Toast.makeText(this, "Good Afternoon!", Toast.LENGTH_SHORT).show()
            welcomeText.text = "Good Afternoon!"
        }
        else if (time in 5..8 && timeDuration.equals("PM")){
//            Toast.makeText(this, "Good Evening!", Toast.LENGTH_SHORT).show()
            welcomeText.text = "Good Evening!"
        }
        else if (time in 9..11 && timeDuration.equals("PM")){
//            Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show()
            welcomeText.text = "Welcome!"
        }
        else if ((time <4 || time == 12)  && timeDuration.equals("AM")){
//            Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show()
            welcomeText.text = "Welcome!"
        }
        else if (time in 5..11 && timeDuration.equals("AM")){
//            Toast.makeText(this, "Good Morning!", Toast.LENGTH_SHORT).show()
            welcomeText.text = "Good Morning!"
        }

    }

    fun getInternetState(){

        val conMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.state == NetworkInfo.State.CONNECTED || conMgr.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI
            )!!.state == NetworkInfo.State.CONNECTED
        ) {
            layoutMain.visibility = View.VISIBLE
            dialogNoInternet.visibility = View.GONE


        } else if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.state == NetworkInfo.State.DISCONNECTED || conMgr.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI
            )!!.state == NetworkInfo.State.DISCONNECTED
        ) {
            layoutMain.visibility = View.GONE
            dialogNoInternet.visibility = View.VISIBLE

        }
    }


}
