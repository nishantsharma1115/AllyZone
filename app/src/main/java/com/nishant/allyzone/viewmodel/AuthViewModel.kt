package com.nishant.allyzone.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nishant.allyzone.repositories.AuthRepository
import com.nishant.allyzone.util.Resource
import com.nishant.allyzone.util.SignUpNavData

class AuthViewModel(
    var authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    val signUpStatus: MutableLiveData<Resource<SignUpNavData>> = MutableLiveData()
    fun signUpUser(user: SignUpNavData) {
        signUpStatus.postValue(Resource.Loading())
        authRepository.signUpUser(user) { task ->
            if (task.isSuccessful) {
                user.userId = task.result?.user?.uid.toString()
                signUpStatus.postValue(Resource.Success(user))
            } else {
                signUpStatus.postValue(Resource.Error("User not Found"))
            }
        }
    }

    val loginStatus: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    fun loginUser(email: String, password: String) {
        loginStatus.postValue(Resource.Loading())
        authRepository.loginUser(email, password, { task ->
            if (task.isSuccessful) {
                loginStatus.postValue(Resource.Success(true))
            } else {
                loginStatus.postValue(Resource.Success(false))
            }
        }, {
            loginStatus.postValue(Resource.Error(it.message.toString()))
        })
    }
}