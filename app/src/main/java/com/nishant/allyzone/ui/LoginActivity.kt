package com.nishant.allyzone.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.nishant.allyzone.R
import com.nishant.allyzone.util.Resource
import com.nishant.allyzone.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.RuntimeException

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        viewModel.loginStatus.observe(this, Observer { response ->
            when (response) {
                is Resource.Loading -> {
                    invalidUsernameDesc.visibility = View.GONE
                    findEmail.visibility = View.GONE
                    wrongPasswordDesc.visibility = View.GONE
                    resetPassword.visibility = View.GONE
                    loadingButton.startAnimation()
                }
                is Resource.Success -> {
                    startActivity(
                        Intent(
                            this,
                            UserDashboardActivity::class.java
                        )
                    )
                    finish()
                }
                is Resource.Error -> {

                    loadingButton.revertAnimation {
                        loadingButton.text = "Login"
                        loadingButton.setBackgroundColor(applicationContext.resources.getColor(R.color.weak))
                    }
                    response.message?.let {
                        if ("There is no user record corresponding to this identifier" in it) {
                            invalidUsernameDesc.visibility = View.VISIBLE
                            findEmail.visibility = View.VISIBLE
                        } else if ("The password is invalid" in it) {
                            wrongPasswordDesc.visibility = View.VISIBLE
                            resetPassword.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })

        signup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        loadingButton.setOnClickListener {
            if (validateCredential()) {
                viewModel.loginUser(edt_email.text.toString(), edt_password.text.toString())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingButton.dispose()
    }

    private fun validateCredential(): Boolean {

        if (edt_email.text.toString().isEmpty()) {
            edt_email.error = "Can not be Empty"
            edt_email.requestFocus()
            return false
        }

        if (!edt_email.text.toString().matches(Patterns.EMAIL_ADDRESS.toRegex())) {
            edt_email.error = "Invalid Email Address"
            edt_email.requestFocus()
            return false
        }

        if (edt_password.text.toString().isEmpty()) {
            edt_password.error = "Can not be Empty"
            edt_password.requestFocus()
            return false
        }

        if (edt_password.text.toString().length < 8) {
            edt_password.error = "Invalid Password"
            edt_password.requestFocus()
            return false
        }

        return true
    }
}
