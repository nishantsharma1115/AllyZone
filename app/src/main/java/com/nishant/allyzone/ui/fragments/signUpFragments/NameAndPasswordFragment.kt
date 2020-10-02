package com.nishant.allyzone.ui.fragments.signUpFragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nishant.allyzone.R
import com.nishant.allyzone.util.PasswordStrengthCalculator
import com.nishant.allyzone.util.SignUpNavData
import kotlinx.android.synthetic.main.fragment_name_and_password.*
import kotlinx.android.synthetic.main.fragment_name_and_password.view.*

class NameAndPasswordFragment : Fragment(R.layout.fragment_name_and_password) {

    private var done = false
    private val passwordStrengthCalculator = PasswordStrengthCalculator()
    private var color: Int = R.color.weak

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edt_password.addTextChangedListener(passwordStrengthCalculator)

        passwordStrengthCalculator.strengthLevel.observe(
            viewLifecycleOwner,
            Observer { strengthLevel ->
                btn_continue.isEnabled = !strengthLevel.contains("Weak")
                txt_strengthLevel.text = strengthLevel
                txt_strengthLevel.setTextColor(ContextCompat.getColor(this.requireContext(), color))
                line_strengthLevel.setBackgroundColor(
                    ContextCompat.getColor(
                        this.requireContext(),
                        color
                    )
                )
            })
        passwordStrengthCalculator.strengthColor.observe(
            viewLifecycleOwner,
            Observer { strengthColor ->
                color = strengthColor
            })
        passwordStrengthCalculator.upperCase.observe(
            viewLifecycleOwner,
            Observer { value ->
                displayPasswordSuggestions(value, checkIcon_upperCase, txt_upperCase)
            })
        passwordStrengthCalculator.lowerCase.observe(
            viewLifecycleOwner,
            Observer { value ->
                displayPasswordSuggestions(value, checkIcon_lowerCase, txt_lowerCase)
            })
        passwordStrengthCalculator.digit.observe(
            viewLifecycleOwner,
            Observer { value ->
                displayPasswordSuggestions(value, checkIcon_digit, txt_digit)
            })
        passwordStrengthCalculator.specialChar.observe(
            viewLifecycleOwner,
            Observer { value ->
                displayPasswordSuggestions(value, checkIcon_specialChar, txt_specialChar)
            })

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

    private fun displayPasswordSuggestions(value: Int, imageView: ImageView, textView: TextView) {
        if (value == 1) {
            imageView.setColorFilter(ContextCompat.getColor(this.requireContext(), R.color.strong))
            textView.setTextColor(ContextCompat.getColor(this.requireContext(), R.color.strong))
        } else {
            imageView.setColorFilter(
                ContextCompat.getColor(
                    this.requireContext(),
                    R.color.light_grey
                )
            )
            textView.setTextColor(ContextCompat.getColor(this.requireContext(), R.color.light_grey))
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
