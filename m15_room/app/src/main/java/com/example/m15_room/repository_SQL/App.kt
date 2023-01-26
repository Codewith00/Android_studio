package com.example.m15_room.repository_SQL

import android.app.Application
import androidx.room.Room

class App : Application() {

    lateinit var db: AppDataBase
        private set

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        db = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,
            "db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    companion object {
        lateinit var INSTANCE: App
            private set
    }
}