package com.example.testapp

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.testapp.api.MainApi
import com.example.testapp.database.QuestionDatabase
import com.example.testapp.databinding.TestPageBinding
import com.example.testapp.model.QuestionEntity
import com.example.testapp.model.QuestionResponse
import com.example.testapp.views.TestViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TestPage : Fragment() {
    private lateinit var binding: TestPageBinding
    private val viewModel: TestViewModel by viewModels<TestViewModel>()
    private val questionDao: QuestionDao by  lazy { context?.let { QuestionDatabase.getInstance(it).questionDao() }!! }
    private var index: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TestPageBinding.inflate(inflater, container, false)

        binding.nameTest.text = "Тест №" + (getArguments()?.getLong("id_test")?.plus(1)).toString()
        binding.endTest.setOnClickListener{
           viewModel.onButtonEnd()
        }

        viewModel.navigateToMainPageFragment.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate){
                findNavController().navigate(R.id.action_testPage_to_mainPage)
                viewModel.onMainPageNavigated()
            }
        })
        if(checkInternetPermission()) {
            loadDataQuestions()
        }

        binding.right.setOnClickListener {
            if (index >= 9) {
                index = 0
            } else {
                index++
            }
            setQuestion(index)
        }

        binding.left.setOnClickListener {
            if (index <= 0){
                index = 9
            } else{
                index --
            }
            setQuestion(index)
        }

        return binding.root
    }

    private fun loadDataQuestions(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://opentdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val quizApi = retrofit.create(MainApi::class.java)

        GlobalScope.launch(Dispatchers.IO) {
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
                                val inQuestions = question.incorrect_answers ?: emptyList<String>()
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
                            saveDataToDatabase(questionsEntities)
                        }
                    }
                    setQuestion(index)
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

    private fun saveDataToDatabase(questions: List<QuestionEntity>) {
        GlobalScope.launch(Dispatchers.IO) {
            questionDao.insertQuestions(questions)
        }
    }

    private fun setQuestion(id: Int){
        GlobalScope.launch(Dispatchers.IO) {
            val question = questionDao.getQuestionById(id)
            binding.question.text = question.question
            binding.a1Text.text = question.correct_answer
            binding.a2Text.text = question.incorrect_answers[0]
            binding.a3Text.text = question.incorrect_answers[1]
            binding.a4Text.text = question.incorrect_answers[2]
        }
    }

    private fun checkInternetPermission(): Boolean {
        val permission = android.Manifest.permission.INTERNET
        return if(ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED){
            true
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), 2)
            false
        }
    }

    private fun outInfo(text: String, outToast: Boolean) {
        Log.d("MINE", text)
        if (!outToast) return
        val toast = Toast.makeText(
            requireActivity().applicationContext,
            text, Toast.LENGTH_SHORT
        )
        toast.show()
    }
}