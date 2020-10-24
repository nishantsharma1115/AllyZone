package com.nishant.allyzone.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nishant.allyzone.modals.User
import com.nishant.allyzone.repositories.DataRepository
import com.nishant.allyzone.util.Resource
import kotlinx.coroutines.launch

class DataViewModel(
    private var dataRepository: DataRepository = DataRepository()
) : ViewModel() {

    val registrationStatus: MutableLiveData<Resource<User>> = MutableLiveData()
    fun registerUser(user: User) {
        registrationStatus.postValue(Resource.Loading())
        dataRepository.registerUser(user, { task ->
            if (task.isSuccessful) {
                registrationStatus.postValue(Resource.Success(user))
            } else {
                registrationStatus.postValue(Resource.Error("User not Found"))
            }
        }, { exception ->
            registrationStatus.postValue(Resource.Error(exception.message.toString()))
        })
    }

    val getUserDataStatus: MutableLiveData<Resource<User>> = MutableLiveData()
    fun getUserData(userId: String) {
        getUserDataStatus.postValue(Resource.Loading())
        dataRepository.getUserData(userId, { document ->
            if (document.data != null) {
                document.toObject(User::class.java)?.let { user ->
                    getUserDataStatus.postValue(Resource.Success(user))
                }
            } else {
                getUserDataStatus.postValue(Resource.Error("User not Found"))
            }
        }, {
            getUserDataStatus.postValue(Resource.Error(it.message.toString()))
        })
    }

    val updateUserDataStatus: MutableLiveData<Resource<User>> = MutableLiveData()
    fun updateUserData(user: User) {
        updateUserDataStatus.postValue(Resource.Loading())
        dataRepository.updateUserData(user, { task ->
            if (task.isSuccessful) {
                updateUserDataStatus.postValue(Resource.Success(user))
            } else {
                updateUserDataStatus.postValue(Resource.Error("Check Internet Connectivity"))
            }
        }, {
            updateUserDataStatus.postValue(Resource.Error(it.message.toString()))
        })
    }

    val uploadProfilePictureStatus: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    fun uploadProfilePicture(
        userId: String,
        file: Uri
    ) = viewModelScope.launch {
        dataRepository.uploadProfilePicture(userId, file, { task ->
            if (task.isSuccessful) {
                uploadProfilePictureStatus.postValue(Resource.Success(true))
            } else {
                uploadProfilePictureStatus.postValue(Resource.Success(false))
            }
        }, { exception ->
            uploadProfilePictureStatus.postValue(Resource.Error(exception.message.toString()))
        })
    }
}