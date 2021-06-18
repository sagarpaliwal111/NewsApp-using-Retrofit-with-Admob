package com.example.retrofitapicheezycode2part

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://newsapi.org/v2/top-headlines?apiKey=086cbf45755841b6a4cf18b75a2898cf&country=in&page=1
val BASE_URL = "https://newsapi.org/"
const val API_KEY = "086cbf45755841b6a4cf18b75a2898cf"


interface NewsInterface {
    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getHeadlines(@Query("country") country: String, @Query("page") page: Int): Call<News>


}

object NewsService {
    val newsInstance: NewsInterface

    init {
        val retofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstance = retofit.create(NewsInterface::class.java)
    }

}

