package com.nishant.allyzone.ui.fragments.signUpFragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.nishant.allyzone.R
import com.nishant.allyzone.util.Resource
import com.nishant.allyzone.util.SignUpNavData
import com.nishant.allyzone.viewmodels.AuthViewModel
import kotlinx.android.synthetic.main.fragment_username.*
import kotlinx.android.synthetic.main.fragment_username.view.*

class UsernameFragment : Fragment(R.layout.fragment_username) {

    private var done = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: AuthViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        viewModel.signUpStatus.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    hideProgressBar()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.d("Error", message)
                    }
                }
            }
        })

        view.btn_continue.setOnClickListener {
            val user = updateUser()
            if (done) {
                viewModel.signUpUser(user)
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
