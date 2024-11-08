package com.example.testapp
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.testapp.databinding.LoginFragmentBinding
import com.example.testapp.views.LoginViewModel

class Login : Fragment() {
    private lateinit var binding: LoginFragmentBinding
    private val viewModel: LoginViewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false)

        binding.ok.setOnClickListener{
            viewModel.onButtonLogin()
        }

        viewModel.navigateToMainPageFragment.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate){
                val result = Bundle()
                result.putString("name", binding.editTextLogin.text.toString())
                findNavController().navigate(R.id.action_login_to_mainPage, result)
                viewModel.onMainPageNavigated()
            }
        })

        return binding.root
    }
    private fun OutInfo(text: String, outToast: Boolean) {
        Log.d("MINE", text)
        if (!outToast) return
        val toast = Toast.makeText(
            requireActivity().applicationContext,
            text, Toast.LENGTH_SHORT
        )
        toast.show()
    }
}