package com.nishant.allyzone.ui.fragments.signUpFragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.nishant.allyzone.R
import com.nishant.allyzone.modals.User
import com.nishant.allyzone.ui.UserDashboardActivity
import com.nishant.allyzone.util.Resource
import com.nishant.allyzone.util.SignUpNavData
import com.nishant.allyzone.viewmodel.AuthViewModel
import com.nishant.allyzone.viewmodel.DataViewModel
import kotlinx.android.synthetic.main.fragment_username.*
import kotlinx.android.synthetic.main.fragment_username.view.*

class UsernameFragment : Fragment(R.layout.fragment_username) {

    private var done = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val authViewModel: AuthViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        val dataViewModel: DataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        authViewModel.signUpStatus.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    hideProgressBar()
                    if (response.data != null) {
                        val user = User(
                            response.data.userId,
                            response.data.username,
                            null,
                            0,
                            0,
                            response.data.fullName,
                            null
                        )
                        dataViewModel.registerUser(user)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.d("Error", message)
                    }
                }
            }
        })
        dataViewModel.registrationStatus.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    hideProgressBar()
                    activity?.startActivity(
                        Intent(activity, UserDashboardActivity::class.java)
                    )
                    activity?.finish()
                }
                is Resource.Error -> {
                    Toast.makeText(activity, response.message, Toast.LENGTH_LONG).show()
                }
            }
        })

        view.btn_continue.setOnClickListener {
            val user = updateUser()
            if (done) {
                authViewModel.signUpUser(user)
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

    private fun updateUser(): SignUpNavData {

        val args: UsernameFragmentArgs by navArgs()
        val user = args.currentUser

        if (edt_username.text.isEmpty()) {
            edt_username.error = "Required"
            edt_username.requestFocus()
        } else {
            user.username = edt_username.text.toString()
            done = true
        }

        return user
    }

}
