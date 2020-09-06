package com.nishant.allyzone.ui.fragments.loginFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.nishant.allyzone.R
import com.nishant.allyzone.ui.UserDashboardActivity

class LoginFragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<TextView>(R.id.signup).setOnClickListener(this)
        view.findViewById<TextView>(R.id.btn_login).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_login -> activity?.startActivity(Intent(activity, UserDashboardActivity::class.java))
            R.id.signup -> navController.navigate(R.id.action_loginFragment_to_createAccountFragment)
        }
    }
}
