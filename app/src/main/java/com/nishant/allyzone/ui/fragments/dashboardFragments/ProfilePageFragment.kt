package com.nishant.allyzone.ui.fragments.dashboardFragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.nishant.allyzone.R
import com.nishant.allyzone.modals.User
import com.nishant.allyzone.ui.EditProfileActivity
import com.nishant.allyzone.util.Resource
import com.nishant.allyzone.viewmodel.DataViewModel
import kotlinx.android.synthetic.main.fragment_profile_page.*

class ProfilePageFragment : Fragment(R.layout.fragment_profile_page) {

    var user: User = User()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId: String = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val viewModel: DataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        viewModel.getUserData(userId)
        viewModel.getUserDataStatus.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    ll_editProfile.isClickable = true
                    ll_editProfile.isFocusable = true
                    response.data?.let {
                        user = it
                        tv_username.text = it.username
                        tv_posts.text = it.noOfPosts.toString()
                        tv_fullName.text = it.name
                        tv_ally.text = it.noOfAlly.toString()
                        if (it.bio != null) tv_bio.text = it.bio
                    }
                }
            }
        })

        if (user.profilePicture != null) {
            Glide.with(this)
                .load(user.profilePicture)
                .into(profilePicture)
        }

        ll_editProfile.setOnClickListener {
            activity?.startActivity(
                Intent(
                    activity,
                    EditProfileActivity::class.java
                ).apply {
                    putExtra("UserData", user)
                }
            )
        }
    }
}
