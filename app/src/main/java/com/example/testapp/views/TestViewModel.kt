package com.example.testapp.views

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.QuestionDao
import com.example.testapp.api.MainApi
import com.example.testapp.model.QuestionEntity
import com.example.testapp.model.QuestionResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TestViewModel : ViewModel() {

    private val _navigateToMainPageFragment = MutableLiveData<Boolean>()
    private val _question = MutableLiveData<String>()
    private val _listAnswers = MutableLiveData<List<String>>()
    private val _correct =  MutableLiveData<Int>()
    private var _index: Int = 0

    val navigateToMainPageFragment: LiveData<Boolean>
        get() = _navigateToMainPageFragment

    val question: MutableLiveData<String>
        get() = _question
    val listAnswers: MutableLiveData<List<String>>
        get() = _listAnswers

    fun onButtonEnd(){
        _navigateToMainPageFragment.value = true
    }

    fun onMainPageNavigated(){
        _navigateToMainPageFragment.value = false
    }

    public suspend fun loadDataQuestions(questionDao: QuestionDao){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://opentdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val quizApi = retrofit.create(MainApi::class.java)

        withContext(Dispatchers.IO) {
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
                            setQuestion(0, questionDao)
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
        }
    }

    public fun AddIndex(questionDao: QuestionDao){
        if (_index >= 9) {
            _index= 0
        } else {
            _index++
        }
        setQuestion(_index, questionDao)
    }

    public fun MinusIndex(questionDao: QuestionDao){
        if (_index <= 0){
            _index = 9
        } else{
            _index--
        }
        setQuestion(_index, questionDao)
    }


    private fun setQuestion(index: Int, questionDao: QuestionDao){
        //_listAnswers.value = listOf("Element1", "Element2", "Element3", "Element4")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val questionGet = questionDao.getQuestionById(index)
                _question.postValue(questionGet.question)
                _listAnswers.postValue(listOf(questionGet.correct_answer, questionGet.incorrect_answers[0],
                    questionGet.incorrect_answers[1], questionGet.incorrect_answers[2]))
            }
        }
    }
}