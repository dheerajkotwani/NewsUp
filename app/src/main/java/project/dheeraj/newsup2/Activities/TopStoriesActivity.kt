package project.dheeraj.newsup2.Activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.layout_top_headlines.*
import project.dheeraj.newsup2.Adapters.HeadlinesRecyclerViewAdapter
import project.dheeraj.newsup2.Model.ArticlesModel
import project.dheeraj.newsup2.Model.NewsHeadlines
import project.dheeraj.newsup2.R
import project.dheeraj.newsup2.Retrofit.ApiInterface
import project.dheeraj.newsup2.Retrofit.RetrofitClient
import project.dheeraj.newsup2.Util.UtilMethods
import project.dheeraj.newsup2.ViewModel.TopStoriesViewModel
import project.dheeraj.newsup2.db.BookmarkDatabase
import project.dheeraj.newsup2.db.BookmarkModel
import retrofit2.Call

class TopStoriesActivity : AppCompatActivity() {

    lateinit var dialogNoInternet: View
    lateinit var layoutNews: View
    lateinit var gifImage: ImageView
    lateinit var headlinesRecyclerView: RecyclerView
    lateinit var skeleton: Skeleton
    lateinit var pageTitle: TextView
    lateinit var title: String
    lateinit var viewModel: TopStoriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_stories_activity)

        val firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        viewModel = ViewModelProviders.of(this).get(TopStoriesViewModel::class.java)

        layoutNews = findViewById(R.id.layout_top_headlines)
        dialogNoInternet = findViewById(R.id.dialog_no_internet)
        gifImage = findViewById(R.id.no_internet_image)
        headlinesRecyclerView = findViewById(R.id.headlines_recycler_view)
        pageTitle = findViewById(R.id.tv_page_title)

        skeleton = headlinesRecyclerView.applySkeleton(project.dheeraj.newsup2.R.layout.shimmer_top_headlines, 10)
        skeleton.maskCornerRadius = 40F
        skeleton.shimmerDurationInMillis = 1500
        skeleton.showSkeleton()

        if(intent.getStringExtra("name")!!.isNotEmpty()){
            pageTitle.text = intent.getStringExtra("name")
            title = intent.getStringExtra("name")!!
        }

        observeNews()
        checkInternet()
        getNews()

    }

    override fun onRestart() {
        checkInternet()
        super.onRestart()
    }

    override fun onResume() {
        checkInternet()
        super.onResume()
    }

    private fun checkInternet(){
        if(!UtilMethods.isInternetAvailable(applicationContext)){
            layoutNews.visibility= View.GONE
            dialogNoInternet.visibility= View.VISIBLE
            Glide.with(this)
                .load(R.drawable.no_internet_gif_2)
                .into(gifImage)
        }
        else{
            layoutNews.visibility = View.VISIBLE
            dialogNoInternet.visibility = View.GONE
        }
    }

    private fun getNews() {

        val apiInterface: ApiInterface = RetrofitClient.getClient().create(
            ApiInterface::class.java
        )

        var call: Call<ArticlesModel> = apiInterface.getArticlesModel()

        when (title) {
            "Entertainment" -> {
                viewModel.getEntertainment()

            }
            "Technology" -> {
                viewModel.getTechnology()
            }
            "Business" -> {
                viewModel.getBusiness()

            }
            "Sports" -> {
                viewModel.getSports()
            }
            "Medical" -> {
                viewModel.getMedical()

            }
            "Science" -> {
                viewModel.getScience()

            }
            "International" -> {
                viewModel.getInternational()

            }
            "Bookmarks" -> {

                BookmarkDatabase(this).bookmarkDao().getBookmarks().observe(this, Observer {

                    var myNewsList = mutableListOf<NewsHeadlines>()
                    for (i in it)
                        if (!i.urlToImage.isNullOrEmpty() ){

                            myNewsList.add(
                                NewsHeadlines(
                                    i.author,
                                    i.id.toString(),
                                    i.name,
                                    i.title,
                                    i.description,
                                    i.url,
                                    i.urlToImage,
                                    i.publishedAt,
                                    i.content
                                )
                            )

                        }

                    myNewsList.reverse()

                    if (myNewsList.isNullOrEmpty())
                        noItem.visibility = View.VISIBLE
                    else {
                        noItem.visibility = View.GONE
                    }

                    headlinesRecyclerView.adapter = HeadlinesRecyclerViewAdapter(applicationContext,myNewsList)
                })

            }
            else -> {
                viewModel.getArticles()
            }
        }
    }

    private fun observeNews() {
        viewModel.liveData.observe(this, Observer {

            var myNewsList = mutableListOf<NewsHeadlines>()
            for (i in it.articles)
            if (!i.urlToImage.isNullOrEmpty() ){

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


            headlinesRecyclerView.adapter = HeadlinesRecyclerViewAdapter(applicationContext,myNewsList)
        })
    }
}
