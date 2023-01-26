package com.example.m15_room.repository_SQL

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
entities = [Word::class],
    version = 1
)
abstract class AppDataBase : RoomDatabase(){
    abstract fun wordDao(): WordDao
}