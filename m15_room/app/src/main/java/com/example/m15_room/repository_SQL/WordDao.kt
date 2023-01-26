package com.example.m15_room.repository_SQL

import androidx.room.*

import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Transaction
    @Query(value = "SELECT * FROM word")
    fun getAll(): Flow<List<Word>>

    @Query(value = "SELECT * FROM word LIMIT 5")
    fun getFive(): Flow<List<Word>>

    @Insert(entity = Word::class,
    onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Word)



    @Query(value = "DELETE FROM word")
    suspend fun delete()

    @Query("UPDATE word SET amount = :amount WHERE id = :id")
    suspend fun update(id: String, amount: Int)
}
