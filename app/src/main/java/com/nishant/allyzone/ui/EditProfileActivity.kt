package com.nishant.allyzone.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.nishant.allyzone.R
import com.nishant.allyzone.modals.User
import com.nishant.allyzone.util.Resource
import com.nishant.allyzone.viewmodel.DataViewModel
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity() {

    var user: User? = User()
    private var isProfilePictureChange: Boolean = false
    private var currFile: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        val viewModel: DataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)

        user = intent.getParcelableExtra("UserData")

        user?.let { initViews(it) }

        icon_save.setOnClickListener {
            updateUser(viewModel)
        }
        icon_back.setOnClickListener {
            this.onBackPressed()
        }
        btn_save.setOnClickListener {
            updateUser(viewModel)
        }
        btn_cancel.setOnClickListener {
            this.onBackPressed()
        }
        tv_changeProfilePicture.setOnClickListener {
            startActivityForResult(
                Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                ),
                1
            )
        }

        viewModel.updateUserDataStatus.observe(this, Observer { response ->
            when (response) {
                is Resource.Loading -> {
                    background_layout.alpha = 0.4F
                    progress_bar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    background_layout.alpha = 1F
                    progress_bar.visibility = View.GONE
                    this.onBackPressed()
                }
                is Resource.Error -> {
                    Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show()
                }
            }
        })
        viewModel.uploadProfilePictureStatus.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    user?.profilePicture = response.data
                }
                is Resource.Error -> {
                    Toast.makeText(this, "Unable to upload Profile Picture", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            isProfilePictureChange = true
            data.data?.let {
                currFile = it
            }
            profilePicture.load(data.data)
        }
    }

    private fun updateUser(viewModel: DataViewModel) {
        user?.let { user ->
            if (isDataChanged(user)) {
                user.apply {
                    this.username = edt_username.text.toString()
                    this.name = edt_name.text.toString()
                    this.bio = edt_bio.text.toString()
                }
                if (isProfilePictureChange) {
                    viewModel.uploadProfilePicture(user.userId, currFile!!)
                }
                viewModel.updateUserData(user)
            } else {
                this.onBackPressed()
            }
        }
    }

    private fun initViews(user: User) {
        edt_username.setText(user.username)
        edt_bio.setText(user.bio)
        edt_name.setText(user.name)
    }

    private fun isDataChanged(user: User): Boolean {

        var isChange = true

        if (edt_name.text.toString() == user.name && edt_username.text.toString() == user.username && edt_bio.text.toString() == user.bio) {
            isChange = false
        }
        Log.d("isDataChange", isChange.toString())

        return isChange
    }
}