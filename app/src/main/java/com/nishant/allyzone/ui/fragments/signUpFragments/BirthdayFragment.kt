package com.nishant.allyzone.ui.fragments.signUpFragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nishant.allyzone.R
import com.nishant.allyzone.util.SignUpNavData
import kotlinx.android.synthetic.main.fragment_birthday.*
import kotlinx.android.synthetic.main.fragment_birthday.view.*

class BirthdayFragment : Fragment(R.layout.fragment_birthday) {

    var done = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.btn_continue.setOnClickListener {
            val user = updateLoginData()
            if (done) {
                findNavController().navigate(
                    BirthdayFragmentDirections.actionBirthdayFragmentToUsernameFragment(
                        user
                    )
                )
            }
        }
    }

    private fun updateLoginData(): SignUpNavData {

        val args: BirthdayFragmentArgs by navArgs()
        val user = args.currentUser

        if (edt_birthday.text.isNotEmpty()) {
            user.dob = edt_birthday.text.toString()
            done = true
        } else {
            edt_birthday.error = "Required"
            edt_birthday.requestFocus()
        }

        return user
    }

}
