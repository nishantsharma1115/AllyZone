package com.nishant.allyzone.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nishant.allyzone.repositories.AuthRepository
import com.nishant.allyzone.util.Resource
import com.nishant.allyzone.util.SignUpNavData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AuthViewModel(
    var authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    val signUpStatus: MutableLiveData<Resource<SignUpNavData>> = MutableLiveData()
    fun signUpUser(user: SignUpNavData) = GlobalScope.launch(Dispatchers.Main) {
        val response = authRepository.signUpUser(user)
        signUpStatus.postValue(response)
    }

    val loginStatus: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    fun loginUser(email: String, password: String) = viewModelScope.launch {
        loginStatus.postValue(Resource.Loading())
        val response = authRepository.loginUser(email, password)
        loginStatus.postValue(response)
    }
}