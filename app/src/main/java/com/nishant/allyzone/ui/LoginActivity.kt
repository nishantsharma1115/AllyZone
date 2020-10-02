package com.nishant.allyzone.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nishant.allyzone.R
import com.nishant.allyzone.util.Resource
import com.nishant.allyzone.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        viewModel.loginStatus.observe(this, Observer { response ->
            when (response) {
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    hideProgressBar()
                    startActivity(
                        Intent(
                            this,
                            UserDashboardActivity::class.java
                        )
                    )
                    finish()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    Toast.makeText(
                        this,
                        "New to AllyZone. Please Sign Up",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })

        signup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        btn_login.setOnClickListener {
            if (validateCredential()) {
                viewModel.loginUser(edt_email.text.toString(), edt_password.text.toString())
            }
        }
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
