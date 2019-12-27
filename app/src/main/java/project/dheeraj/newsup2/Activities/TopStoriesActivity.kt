package project.dheeraj.newsup2.Activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.google.firebase.analytics.FirebaseAnalytics
import project.dheeraj.newsup2.Adapters.HeadlinesRecyclerViewAdapter
import project.dheeraj.newsup2.Model.ArticlesModel
import project.dheeraj.newsup2.Model.NewsHeadlines
import project.dheeraj.newsup2.R
import project.dheeraj.newsup2.Retrofit.ApiInterface
import project.dheeraj.newsup2.Retrofit.RetrofitClient
import project.dheeraj.newsup2.Util.UtilMethods
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopStoriesActivity : AppCompatActivity() {

    lateinit var dialogNoInternet: View
    lateinit var layoutNews: View
    lateinit var gifImage: ImageView
    lateinit var headlinesRecyclerView: RecyclerView
    lateinit var skeleton: Skeleton
    lateinit var pageTitle: TextView
    lateinit var title: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_stories_activity)

        val firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        layoutNews = findViewById(R.id.layout_top_headlines)
        dialogNoInternet = findViewById(R.id.dialog_no_internet)
        gifImage = findViewById(R.id.no_internet_image)
        headlinesRecyclerView = findViewById(R.id.headlines_recycler_view)
        pageTitle = findViewById(R.id.tv_page_title)

        skeleton = headlinesRecyclerView.applySkeleton(project.dheeraj.newsup2.R.layout.shimmer_top_headlines, 10)
        skeleton.maskCornerRadius = 40F
        skeleton.shimmerDurationInMillis = 1500
        skeleton.showSkeleton()

        if(intent.getStringExtra("name").isNotEmpty()){
            pageTitle.text = intent.getStringExtra("name")
            title = intent.getStringExtra("name")
        }


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

    fun checkInternet(){
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

        if(title == "Entertainment"){
            call = apiInterface.getEntertainmaent()

        }
        else if(title == "Technology"){
            call = apiInterface.getTechnology()

        }
        else if(title == "Business"){
            call = apiInterface.getBusiness()

        }
        else if(title == "Sports"){
            call = apiInterface.getSports()

        }
        else if(title == "Medical"){
            call = apiInterface.getHealth()

        }
        else if(title == "Science"){
            call = apiInterface.getScience()

        }
        else if(title == "International"){
            call = apiInterface.getInternational()

        }
        else {
            call = apiInterface.getArticlesModel()
        }
        call.enqueue(object : Callback<ArticlesModel> {
            override fun onFailure(
                call: Call<ArticlesModel>?,
                t: Throwable?
            ) {
                Toast.makeText(applicationContext, "Error Loading Data", Toast.LENGTH_SHORT).show()
                Log.e("Error", t?.message.toString())
//                homeSwipeRefreshLayout.isRefreshing = false
            }

            override fun onResponse(
                call: Call<ArticlesModel>?,
                response: Response<ArticlesModel>?
            ) {

                var myNewsList = mutableListOf<NewsHeadlines>()

                if(response != null){
                    if(response.body() != null){
                        for(i in 0 until response.body()!!.articles.size){

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

//                            homeSwipeRefreshLayout.isRefreshing = false

                        }
                    }
                }

//                topStoriesRecyclerView.adapter = TopStoriesHomeRecyclerViewAdapter(applicationContext, myNewsList)
                headlinesRecyclerView.adapter = HeadlinesRecyclerViewAdapter(applicationContext, myNewsList)

                Log.d("Total Results: ", response?.body()?.totalResults.toString())
                Log.d("List Items: ", myNewsList.size.toString())

            }

        })
    }
}
