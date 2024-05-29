package com.example.testapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestion(cocktail: QuestionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestions(cocktails: List<QuestionEntity>): List<Long>

    @Query("SELECT * FROM questions")
    public suspend fun getCocktails(): List<QuestionEntity>

    @Query("SELECT * FROM questions WHERE id = :questionId")
    public suspend fun getQuestionById(questionId: Int): QuestionEntity

    @Query("DELETE FROM questions WHERE id = :questionId")
    public suspend fun deleteQuestionById(questionId: Int)

    @Query("SELECT * FROM questions")
    public fun getAllQuestions(): List<QuestionEntity>

    @Query("DELETE FROM questions")
    public fun deleteAllQuestions(): Int // Метод для удаления всех коктейлей из базы данных

    @Query("DELETE FROM questions WHERE question NOT IN (SELECT id FROM questions ORDER BY id DESC LIMIT 15)")
    public fun deleteExcessQuestions()

    @Delete
    fun deleteQuestions(cocktails: List<QuestionEntity>)
}