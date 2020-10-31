package com.nishant.allyzone.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nishant.allyzone.R
import com.nishant.allyzone.util.LoginActivityUtils
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
                    invalidUsernameDesc.visibility = View.GONE
                    findEmail.visibility = View.GONE
                    wrongPasswordDesc.visibility = View.GONE
                    resetPassword.visibility = View.GONE
                    loadingButton.setBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.white
                        )
                    )
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

            val credentials = LoginActivityUtils.validateCredentials(
                edt_email.text.toString(),
                edt_password.text.toString()
            )

            when (credentials) {
                "EMAIL_EMPTY" -> {
                    edt_email.error = "Can not be Empty"
                    edt_email.requestFocus()
                }
                "PASSWORD_EMPTY" -> {
                    edt_password.error = "Can not be Empty"
                    edt_password.requestFocus()
                }
                "EMAIL_INVALID" -> {
                    edt_email.error = "Invalid Email Address"
                    edt_email.requestFocus()
                }
                "PASSWORD_SMALL" -> {
                    edt_password.error = "Invalid Password"
                    edt_password.requestFocus()
                }
                "VALIDATE" -> {
                    viewModel.loginUser(edt_email.text.toString(), edt_password.text.toString())
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingButton.dispose()
    }
}
