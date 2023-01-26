package com.example.myapplication

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "https://api.thecatapi.com"

object RetrofitServ {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val searchImageApi: SearchImageApi = retrofit.create(SearchImageApi::class.java)
}
interface SearchImageApi {
//    @Headers(
//        "Accept: application/json",
//        "Content-type: application/json")
    @GET("/v1/images/search")
    fun getCatImagesList(): Call<List<Cats>>
}
//@JsonClass(generateAdapter = true)
data class Cats(
    @SerializedName("id")val id: String,
    @SerializedName("url")val url: String
)