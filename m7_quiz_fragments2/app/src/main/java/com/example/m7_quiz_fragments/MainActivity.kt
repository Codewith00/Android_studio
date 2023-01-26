package com.example.m7_quiz_fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        supportFragmentManager.commit {
//            replace<WelcomeFragment>(R.id.nav_host_fragment_container)
//            addToBackStack(WelcomeFragment::class.java.simpleName)
//        }
    }
}