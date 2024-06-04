package com.example.testapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.testapp.activities.PagesActivity
import com.example.testapp.activities.TestActivity
import com.example.testapp.data.question.Question
import com.example.testapp.data.question.TestDao
import com.example.testapp.data.question.TestDatabase
import com.example.testapp.data.question.TestEntity
import com.example.testapp.databinding.FragmentQuestionsCreateBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuestionsCreate : Fragment() {
    private lateinit var binding: FragmentQuestionsCreateBinding
    private val testDao: TestDao by lazy { context?.let { TestDatabase.getInstance(it).testDao() }!! }
    private var test: TestEntity? = null
    private var index: Int = 0
    private var questions: MutableList<Question> = mutableListOf()
    private var max = 10

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuestionsCreateBinding.inflate(inflater, container, false)


        val res = arguments?.getInt("test_index")

        GlobalScope.launch(Dispatchers.IO) {
            test = res?.let { testDao.geTestById(it) }
            questions = test?.questions as MutableList<Question>
            max = test!!.count
            withContext(Dispatchers.Main) {
                setIndex()
                setQuestion(0)
            }
        }

        binding.right.setOnClickListener {
            saveQuestion()
            if (index >= max) {
                index = 0
            } else {
                index++
            }
            setQuestion(index)
            setIndex()
        }

        binding.left.setOnClickListener {
            saveQuestion()
            if (index <= 0){
                //index = max
            } else{
                index --
            }
            setQuestion(index)
            setIndex()
        }

        binding.endTest.setOnClickListener {
            saveQuestion()
            test?.questions = questions.toList()
            GlobalScope.launch(Dispatchers.IO) {
                testDao.insertTest(test!!)
                withContext(Dispatchers.Main) {
                    val intent = Intent(requireActivity(), PagesActivity::class.java)
                    startActivity(intent)
                }
            }
        }


        return binding.root
    }


    private fun setIndex(){
        binding.index.text = (index+1).toString() + "/" + (max+1).toString()
    }

    private fun setQuestion(idQuestion: Int){
        index = idQuestion
        if(index >= questions.size){
            binding.question.text.clear()
            binding.a1.text.clear()
            binding.a2.text.clear()
            binding.a3.text.clear()
            binding.a4.text.clear()
        }
        else{
            val question = questions.get(index)
            binding.question.setText(question.question.toString())
            binding.a1.setText(question.correct_answer.toString())
            binding.a2.setText(question.incorrect_answers?.get(0).toString())
            binding.a3.setText(question.incorrect_answers?.get(1).toString())
            binding.a4.setText(question.incorrect_answers?.get(2).toString())
        }
    }

    private fun saveQuestion() {
        var newQuestion = Question(
            binding.question.text.toString(),
            binding.a1.text.toString(),
            listOf(
                binding.a2.text.toString(),
                binding.a3.text.toString(),
                binding.a4.text.toString()
            )
        )

        if (index >= questions.size) {
            questions.add(newQuestion)
        } else {
            questions[index] = newQuestion
        }
    }
}