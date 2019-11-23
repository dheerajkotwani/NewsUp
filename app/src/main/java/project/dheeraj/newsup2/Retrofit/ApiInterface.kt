package project.dheeraj.newsup2.Retrofit

import project.dheeraj.newsup2.Model.ArticlesModel
import project.dheeraj.newsup2.Util.UtilConstants.apiKey
import project.dheeraj.newsup2.Util.UtilConstants.country
import project.dheeraj.newsup2.Util.UtilConstants.pageSize
import project.dheeraj.newsup2.Util.UtilConstants.topHeadlines
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {


    @GET("$topHeadlines$country=in&$pageSize=100&$apiKey")
    fun getArticlesModel() : Call<ArticlesModel>


}