package com.nishant.allyzone.ui.fragments.loginFragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nishant.allyzone.R
import com.nishant.allyzone.ui.LoginActivity
import com.nishant.allyzone.ui.UserDashboardActivity
import com.nishant.allyzone.util.Resource
import com.nishant.allyzone.viewmodels.AuthViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(activity as LoginActivity).get(AuthViewModel::class.java)
        view.signup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_createAccountFragment)
        }
        view.btn_login.setOnClickListener {

            if (validateCredential()) {
                loginUser(viewModel)
            }
        }
    }

    private fun loginUser(
        viewModel: AuthViewModel
    ) {
        viewModel.loginUser(edt_email.text.toString(), edt_password.text.toString())
        viewModel.loginStatus.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    activity?.startActivity(
                        Intent(
                            activity,
                            UserDashboardActivity::class.java
                        )
                    )
                    activity?.finish()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.d("Error", message)
                    }
                }
            }
        })
    }

    private fun hideProgressBar() {
        progress_bar.visibility = View.INVISIBLE
        background_layout.alpha = 1F
    }

    private fun showProgressBar() {
        progress_bar.visibility = View.VISIBLE
        background_layout.alpha = 0.4F
    }

    private fun validateCredential(): Boolean {

        var isCorrect = true

        if (edt_email.text.toString().isEmpty()) {
            edt_email.error = "Can not be Empty"
            edt_email.requestFocus()
            isCorrect = false
            return isCorrect
        }

        if (!edt_email.text.toString().matches(Patterns.EMAIL_ADDRESS.toRegex())) {
            edt_email.error = "Invalid Email Address"
            edt_email.requestFocus()
            isCorrect = false
            return isCorrect
        }

        if (edt_password.text.toString().isEmpty()) {
            edt_password.error = "Can not be Empty"
            edt_password.requestFocus()
            isCorrect = false
            return isCorrect
        }

        if (edt_password.text.toString().length < 8) {
            edt_password.error = "Invalid Password"
            edt_password.requestFocus()
            isCorrect = false
            return isCorrect
        }

        return isCorrect
    }
}
