package com.example.testapp
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.testapp.databinding.LoginFragmentBinding

class Login : Fragment() {
    private lateinit var binding: LoginFragmentBinding;
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.login.setOnClickListener{
            viewModel.onButtonLogin()
        }

        viewModel.navigateToMainPageFragment.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate){
                val intent = Intent(requireActivity(), PagesActivity::class.java)
                startActivity(intent)
                viewModel.onMainPageNavigated()
            }
        })

        binding.register.setOnClickListener{
            findNavController().navigate(R.id.action_login_to_register2)
        }

        return binding.root
    }
}