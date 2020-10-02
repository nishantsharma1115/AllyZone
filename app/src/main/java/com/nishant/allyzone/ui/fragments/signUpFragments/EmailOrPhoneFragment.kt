package com.nishant.allyzone.ui.fragments.signUpFragments

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nishant.allyzone.R
import com.nishant.allyzone.util.SignUpNavData
import kotlinx.android.synthetic.main.fragment_email_or_phone.*
import kotlinx.android.synthetic.main.fragment_email_or_phone.view.*

class EmailOrPhoneFragment : Fragment(R.layout.fragment_email_or_phone) {

    private val user = SignUpNavData()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.btn_next.setOnClickListener {
            if (updateLoginData() && validateEmail()) {
                findNavController().navigate(
                    EmailOrPhoneFragmentDirections.actionEmailOrPhoneFragmentToNameAndPasswordFragment(
                        user
                    )
                )
            }
        }

        view.login.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun validateEmail(): Boolean {

        var isCorrect = true

        if (edt_phoneOrEmail.text.toString().isEmpty()) {
            edt_phoneOrEmail.error = "Can not be Empty"
            edt_phoneOrEmail.requestFocus()
            isCorrect = false
            return isCorrect
        }

        if (!edt_phoneOrEmail.text.toString().matches(Patterns.EMAIL_ADDRESS.toRegex())) {
            edt_phoneOrEmail.error = "Invalid Email Address"
            edt_phoneOrEmail.requestFocus()
            isCorrect = false
            return isCorrect
        }

        return isCorrect
    }

    private fun updateLoginData(): Boolean {

        var done = false

        if (edt_phoneOrEmail.text.isNotEmpty()) {
            user.email = edt_phoneOrEmail.text.toString()
            done = true
        } else {
            edt_phoneOrEmail.error = "Required"
            edt_phoneOrEmail.requestFocus()
        }

        return done
    }

}
