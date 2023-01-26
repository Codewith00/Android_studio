package com.example.m16_architecture.data

import com.example.m16_architecture.entity.UsefulActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import javax.inject.Inject

class UsefulActivitiesRepository @Inject constructor() {

    suspend fun getUsefulActivity(): UsefulActivity {
        return RetrofitServices.searchActivityApi.getPersonValue()
    }

    object RetrofitServices {
        private val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val searchActivityApi: ActivityApi = retrofit.create(ActivityApi::class.java)
    }

    interface ActivityApi {

        @GET("/api/activity/")
        suspend fun getPersonValue(): UsefulActivityDTO
    }

    companion object {
        private const val URL = "https://www.boredapi.com"
    }
}