package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import coil.load
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val textView = findViewById<TextView>(R.id.textView)

        RetrofitServ.searchImageApi.getCatImagesList()
            .enqueue(object : Callback<List<Cats>>{
            override fun onResponse(call: Call<List<Cats>>, response: Response<List<Cats>>) {
                if (response.isSuccessful){
                val cat = response.body()?.first() ?: return
                val status = response.code()
                imageView.load(cat.url)}
            }

            override fun onFailure(call: Call<List<Cats>>, t: Throwable) {
                Log.e("ERROR", "Something went wrong", t)
            }

        })

    }
}