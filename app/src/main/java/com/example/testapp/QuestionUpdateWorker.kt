package com.example.testapp

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.testapp.api.MainApi
import com.example.testapp.database.QuestionDatabase
import com.example.testapp.model.QuestionEntity
import com.example.testapp.model.QuestionResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuestionUpdateWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val questionDao: QuestionDao by lazy { QuestionDatabase.getInstance(appContext).questionDao() }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://opentdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val quizApi: MainApi by lazy { retrofit.create(MainApi::class.java) }
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val call = quizApi.getQuiz(10, 23, "multiple")
            call.enqueue(object : Callback<QuestionResponse> {
                override fun onResponse(
                    call: Call<QuestionResponse>,
                    response: Response<QuestionResponse>
                ) {
                    if (response.isSuccessful) {
                        val questionResponse = response.body()
                        val questions = questionResponse?.results
                        var questionId: Int = 0
                        questions?.let { questionList ->
                            val questionsEntities = mutableListOf<QuestionEntity>()
                            for (question in questionList) {
                                val inQuestions =
                                    question.incorrect_answers ?: emptyList<String>()
                                questionsEntities.add(
                                    QuestionEntity(
                                        id = questionId,
                                        question = question.question,
                                        correct_answer = question.correct_answer,
                                        incorrect_answers = inQuestions
                                    )
                                )
                                questionId++
                            }
                            questionDao.insertQuestions(questionsEntities)
                        }
                    }
                }

                override fun onFailure(call: Call<QuestionResponse>, t: Throwable) {
                    Log.d(
                        "AddedCocktail",
                        "Error"
                    )
                }
            })
            Result.success()
        } catch (e: Exception) {
            Log.e("CocktailUpdateWorker", "Error updating cocktails: ${e.message}")
            Result.failure()
        }

    }
}