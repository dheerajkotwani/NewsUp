package project.dheeraj.newsup2.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_main_screen.*
import project.dheeraj.newsup2.Adapters.TopStoriesHomeRecyclerViewAdapter
import project.dheeraj.newsup2.Model.NewsHeadlines
import project.dheeraj.newsup2.R
import project.dheeraj.newsup2.Retrofit.ApiInterface
import project.dheeraj.newsup2.Retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import project.dheeraj.newsup2.Model.ArticlesModel as ArticlesModel1

class MainActivity : AppCompatActivity() {

    lateinit var topStories: TextView
    lateinit var topStoriesRecyclerView : RecyclerView
    lateinit var topStoriesAdapter : TopStoriesHomeRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        topStories = findViewById(R.id.view_all_top_stories)
        topStoriesRecyclerView = findViewById(R.id.top_stories_recycler_view)

        topStories.setOnClickListener {
            intent  = Intent(this, TopStoriesActivity::class.java)
            startActivity(intent)
        }

        getNews()


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
                Log.e("Error", t?.message.toString())
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
                                    "Data " + i.toString(),
                                    (response.body()!!.articles.get(i).author +
                                            response.body()!!.articles.get(i).title +
                                            response.body()!!.articles.get(i).description +
                                            response.body()!!.articles.get(i).url +
                                            response.body()!!.articles.get(i).urlToImage +
                                            response.body()!!.articles.get(i).publishedAt +
                                            response.body()!!.articles.get(i).content)
                                )
                            }
                        }
                    }
                }

                topStoriesRecyclerView.adapter = TopStoriesHomeRecyclerViewAdapter(applicationContext, myNewsList)




                Toast.makeText(
                    this@MainActivity,
                    response?.body()?.totalResults.toString(),
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("Response", response?.body()?.totalResults.toString())
                Log.d("List Items", myNewsList.size.toString())

            }

        })
    }


}
