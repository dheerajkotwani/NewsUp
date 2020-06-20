package project.dheeraj.newsup2.Retrofit

import project.dheeraj.newsup2.Model.ArticlesModel
import project.dheeraj.newsup2.Util.UtilConstants
import project.dheeraj.newsup2.Util.UtilConstants.apiKey
import project.dheeraj.newsup2.Util.UtilConstants.category
import project.dheeraj.newsup2.Util.UtilConstants.country
import project.dheeraj.newsup2.Util.UtilConstants.everything
import project.dheeraj.newsup2.Util.UtilConstants.language
import project.dheeraj.newsup2.Util.UtilConstants.pageSize
import project.dheeraj.newsup2.Util.UtilConstants.topHeadlines
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {

    companion object{
        operator fun invoke(): ApiInterface {
            return Retrofit.Builder()
                .baseUrl(UtilConstants.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
        }
    }


    @GET("$topHeadlines$country=in&$pageSize=100&$apiKey")
    fun getArticlesModel() : Call<ArticlesModel>

    @GET("$topHeadlines$country=in&$pageSize=100&$apiKey")
    suspend fun getTopHeadlines() : Response<ArticlesModel>

    @GET("$topHeadlines$category=technology&$country=in&$pageSize=100&$apiKey")
    fun getPreferences() : Call<ArticlesModel>

    @GET("$topHeadlines$category=technology&$country=in&$pageSize=100&$apiKey")
    suspend fun getArticles() : Response<ArticlesModel>

    @GET("$topHeadlines$category=technology&$country=in&$pageSize=100&$apiKey")
    suspend fun getTechnology() : Response<ArticlesModel>

    @GET("$topHeadlines$category=entertainment&$country=in&$pageSize=100&$apiKey")
    fun getEntertainmaent() : Call<ArticlesModel>

    @GET("$topHeadlines$category=entertainment&$country=in&$pageSize=100&$apiKey")
    suspend fun getEntertainment() : Response<ArticlesModel>

    @GET("$topHeadlines$category=business&$country=in&$pageSize=100&$apiKey")
    suspend fun getBusiness() : Response<ArticlesModel>

    @GET("$topHeadlines$category=general&$country=in&$pageSize=100&$apiKey")
    fun getGeneral() : Call<ArticlesModel>

    @GET("$topHeadlines$category=health&$country=in&$pageSize=100&$apiKey")
    suspend fun getHealth() : Response<ArticlesModel>

    @GET("$topHeadlines$category=science&$country=in&$pageSize=100&$apiKey")
    suspend fun getScience() : Response<ArticlesModel>

    @GET("$topHeadlines$category=sports&$country=in&$pageSize=100&$apiKey")
    suspend fun getSports() : Response<ArticlesModel>

    @GET("$everything$language=en&q=international&$pageSize=100&$apiKey")
    suspend fun getInternational() : Response<ArticlesModel>

}