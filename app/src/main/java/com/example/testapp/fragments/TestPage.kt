package com.example.testapp.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.data.question.MainApi
import com.example.testapp.data.question.QuestionDao
import com.example.testapp.data.question.QuestionDatabase
import com.example.testapp.data.question.QuestionEntity
import com.example.testapp.data.question.QuestionResponse
import com.example.testapp.databinding.TestPageBinding
import com.example.testapp.view_models.TestViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class TestPage : Fragment() {
    private lateinit var binding: TestPageBinding;
    private lateinit var viewModel: TestViewModel
    private val questionDao: QuestionDao by lazy { context?.let { QuestionDatabase.getInstance(it).questionDao() }!! }
    private var index: Int = 0
    private var answers: MutableList<Int> = mutableListOf()
    private var correct: MutableList<Boolean> = mutableListOf()
    private var max: Int = 9
    private lateinit var saveQuestion: QuestionEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TestPageBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(TestViewModel::class.java)


        binding.endTest.setOnClickListener{
           viewModel.onButtonEnd()
        }

        viewModel.navigateToMainPageFragment.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate){
                val bundle = Bundle().apply {
                    putBooleanArray("result", correct.toBooleanArray())
                }
                findNavController().navigate(R.id.result, bundle)
                viewModel.onMainPageNavigated()
            }
        })

        if(checkInternetPermission()) {
            loadDataQuestions()
        }


        binding.right.setOnClickListener {
            if (index >= max) {
                index = 0
            } else {
                index++
            }
            setQuestion(index)
            setIndex()
            loadAnswer()
        }

        binding.left.setOnClickListener {
            if (index <= 0){
                index = max
            } else{
                index --
            }
            setQuestion(index)
            setIndex()
            loadAnswer()
        }


        binding.a1Text.setOnClickListener{
            selectAnswer(0)
        }

        binding.a2Text.setOnClickListener{
            selectAnswer(1)
        }

        binding.a3Text.setOnClickListener{
            selectAnswer(2)
        }

        binding.a4Text.setOnClickListener{
            selectAnswer(3)
        }

        return binding.root
    }

    private fun setIndex(){
        binding.index.text = (index+1).toString() + "/" + (max+1).toString()
    }

    private fun loadAnswer(){
        if(answers[index] == -1){
            resetAnswers()
            return
        }
        selectAnswer(answers[index])
    }

    private fun selectAnswer(id: Int){
        resetAnswers()

        when(id){
            0 ->  binding.a1Text.setBackgroundResource(R.drawable.ans_back_choose)
            1 ->  binding.a2Text.setBackgroundResource(R.drawable.ans_back_choose)
            2 ->  binding.a3Text.setBackgroundResource(R.drawable.ans_back_choose)
            3 ->  binding.a4Text.setBackgroundResource(R.drawable.ans_back_choose)
        }

        if(id == saveQuestion.incorrect_answers.indexOf(saveQuestion.correct_answer)){
            correct[index] = true
        }
        else{
            correct[index] = false
        }


        answers[index] = id
    }

    private fun resetAnswers(){
        binding.a1Text.setBackgroundResource(R.drawable.answer_back)
        binding.a2Text.setBackgroundResource(R.drawable.answer_back)
        binding.a3Text.setBackgroundResource(R.drawable.answer_back)
        binding.a4Text.setBackgroundResource(R.drawable.answer_back)
    }

    private fun loadDataQuestions(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://opentdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val quizApi = retrofit.create(MainApi::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val call = quizApi.getQuiz(10, MainPage.getInstance().category, "multiple")
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
                                val allAnswers = mutableListOf(question.correct_answer).apply {
                                    addAll(inQuestions)
                                }
                                allAnswers.shuffle(Random)
                                questionsEntities.add(
                                    QuestionEntity(
                                        id =  questionId,
                                        question = Html.fromHtml(question.question, Html.FROM_HTML_MODE_LEGACY)
                                            .toString(),
                                        correct_answer = question.correct_answer,
                                        incorrect_answers = allAnswers
                                    )
                                )
                                questionId++
                            }
                            max = questionId - 1
                            for (i in 0..max) {
                                answers.add(-1)
                                correct.add(false)
                            }
                            saveDataToDatabase(questionsEntities)
                            setIndex()
                            setQuestion(index)
                        }
                    }
                }

                override fun onFailure(call: Call<QuestionResponse>, t: Throwable) {
                    Log.d(
                        "AddedTest",
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
            saveQuestion = questionDao.getQuestionById(id)
            binding.question.text = saveQuestion.question

            outInfo(saveQuestion.correct_answer.toString(), false)
            binding.a1Text.text = saveQuestion.incorrect_answers[0]
            binding.a2Text.text = saveQuestion.incorrect_answers[1]
            binding.a3Text.text = saveQuestion.incorrect_answers[2]
            binding.a4Text.text = saveQuestion.incorrect_answers[3]
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