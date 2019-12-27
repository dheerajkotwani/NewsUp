package project.dheeraj.newsup2.Retrofit

import project.dheeraj.newsup2.Model.ArticlesModel
import project.dheeraj.newsup2.Util.UtilConstants.apiKey
import project.dheeraj.newsup2.Util.UtilConstants.category
import project.dheeraj.newsup2.Util.UtilConstants.country
import project.dheeraj.newsup2.Util.UtilConstants.everything
import project.dheeraj.newsup2.Util.UtilConstants.language
import project.dheeraj.newsup2.Util.UtilConstants.pageSize
import project.dheeraj.newsup2.Util.UtilConstants.topHeadlines
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

//    val sharedPreferences = contex
//        applicationgetSharedPreferences(SHARED_PREF, MODE_PRIVATE);



    @GET("$topHeadlines$country=in&$pageSize=100&$apiKey")
    fun getArticlesModel() : Call<ArticlesModel>

//    @GET("category=technology&country=in&pageSize=100&apiKey=34224b5a549e412eb21ca3c30258f4d5")
    @GET("$topHeadlines$category=technology&$country=in&$pageSize=100&$apiKey")
    fun getPreferences() : Call<ArticlesModel>

     @GET("$topHeadlines$category=technology&$country=in&$pageSize=100&$apiKey")
        fun getTechnology() : Call<ArticlesModel>

     @GET("$topHeadlines$category=entertainment&$country=in&$pageSize=100&$apiKey")
        fun getEntertainmaent() : Call<ArticlesModel>

     @GET("$topHeadlines$category=business&$country=in&$pageSize=100&$apiKey")
        fun getBusiness() : Call<ArticlesModel>

     @GET("$topHeadlines$category=general&$country=in&$pageSize=100&$apiKey")
        fun getGeneral() : Call<ArticlesModel>

     @GET("$topHeadlines$category=health&$country=in&$pageSize=100&$apiKey")
        fun getHealth() : Call<ArticlesModel>

     @GET("$topHeadlines$category=science&$country=in&$pageSize=100&$apiKey")
        fun getScience() : Call<ArticlesModel>

     @GET("$topHeadlines$category=sports&$country=in&$pageSize=100&$apiKey")
        fun getSports() : Call<ArticlesModel>

     @GET("$everything$language=en&q=international&$pageSize=100&$apiKey")
        fun getInternational() : Call<ArticlesModel>


}