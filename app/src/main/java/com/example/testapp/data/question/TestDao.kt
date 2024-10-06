package com.example.testapp.data.question

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTest(question: TestEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTestes(questions: List<TestEntity>): List<Long>

    @Update
    suspend fun updateTest(test: TestEntity)

    @Query("SELECT * FROM testes")
    public suspend fun getTestes(): List<TestEntity>

    @Query("SELECT * FROM testes WHERE id = :testId")
    public suspend fun geTestById(testId: Int): TestEntity

    @Query("DELETE FROM testes WHERE id = :testID")
    public suspend fun deleteTestById(testID: Int)

    @Query("SELECT * FROM testes")
    public fun getAllTestes(): List<TestEntity>

    @Query("DELETE FROM testes")
    public fun deleteAllTestes(): Int

    @Delete
    fun deleteTestes(testes: List<TestEntity>)
}