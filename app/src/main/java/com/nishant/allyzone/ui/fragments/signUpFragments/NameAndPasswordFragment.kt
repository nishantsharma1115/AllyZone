package com.nishant.allyzone.ui.fragments.signUpFragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nishant.allyzone.R
import com.nishant.allyzone.util.SignUpNavData
import kotlinx.android.synthetic.main.fragment_name_and_password.*
import kotlinx.android.synthetic.main.fragment_name_and_password.view.*

class NameAndPasswordFragment : Fragment(R.layout.fragment_name_and_password) {

    private var done = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.btn_continue.setOnClickListener {
            val user = updateUser()
            if (done) {
                findNavController().navigate(
                    NameAndPasswordFragmentDirections.actionNameAndPasswordFragmentToBirthdayFragment(
                        user
                    )
                )
            }
        }
    }

    private fun updateUser(): SignUpNavData {

        val args: NameAndPasswordFragmentArgs by navArgs()
        val user = args.currentUser

        when {
            edt_fullName.text.isEmpty() -> {
                edt_fullName.error = "Required"
                edt_fullName.requestFocus()
            }
            edt_password.text.isEmpty() -> {
                edt_password.error = "Required"
                edt_password.requestFocus()
            }
            edt_password.text.toString() != edt_confirmPassword.text.toString() -> {
                edt_confirmPassword.error = "Password and Confirm Password should be equal"
            }
            else -> {
                user.fullName = edt_fullName.text.toString()
                user.password = edt_password.text.toString()
                done = true
            }
        }

        return user
    }

}
