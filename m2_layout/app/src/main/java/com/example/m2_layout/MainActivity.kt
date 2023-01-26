package com.example.m2_layout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.m2_layout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.customButton1.setDownText("Этот текст был задан программой")
        binding.customButton1.setUpText("Здесь могла быть ваша реклама")

    }
}