package com.example.My_first_project

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.android.HandlerDispatcher

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        supportActionBar?.hide()

        Handler().postDelayed({
            val intent = Intent(this@MainActivity, SplashActiv::class.java)
            startActivity(intent)
        }, 3000)
}
}