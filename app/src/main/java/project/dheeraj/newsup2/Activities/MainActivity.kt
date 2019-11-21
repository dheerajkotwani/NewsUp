package project.dheeraj.newsup2.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import project.dheeraj.newsup2.R
import project.dheeraj.newsup2.Retrofit.ApiInterface
import project.dheeraj.newsup2.Retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import project.dheeraj.newsup2.Model.ArticlesModel as ArticlesModel1

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiInterface : ApiInterface = RetrofitClient.getClient().create(
            ApiInterface::class.java)
        val call : Call<ArticlesModel1> = apiInterface.getArticlesModel()
        call.enqueue(object : Callback<project.dheeraj.newsup2.Model.ArticlesModel>{
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
                Toast.makeText(this@MainActivity, response?.body()?.totalResults.toString(),Toast.LENGTH_SHORT ).show()
                Log.d("Response",response?.body()?.totalResults.toString())

            }

        })




    }
}
