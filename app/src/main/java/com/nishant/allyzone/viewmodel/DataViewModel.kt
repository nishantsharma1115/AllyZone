package com.nishant.allyzone.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nishant.allyzone.modals.User
import com.nishant.allyzone.repositories.DataRepository
import com.nishant.allyzone.util.Resource

class DataViewModel(
    var dataRepository: DataRepository = DataRepository()
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
}