package com.nishant.allyzone.repositories

import com.google.firebase.auth.FirebaseAuth
import com.nishant.allyzone.util.Resource
import com.nishant.allyzone.util.SignUpNavData

class AuthRepository {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun signUpUser(
        user: SignUpNavData
    ): Resource<SignUpNavData> {

        var response: Resource<SignUpNavData> = Resource.Loading()

        mAuth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                response = if (task.isSuccessful) {
                    user.userId = task.result?.user?.uid.toString()
                    Resource.Success(user)
                } else {
                    Resource.Error("Error while Authentication")
                }
            }
            .addOnFailureListener { it ->
                response = Resource.Error(it.message.toString())
            }

        return response
    }

    fun loginUser(
        email: String,
        password: String
    ): Resource<Boolean> {

        var response: Resource<Boolean> = Resource.Loading()

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                response = if (task.isSuccessful) {
                    Resource.Success(true)
                } else {
                    Resource.Error("Error while Login, check your Internet Connection")
                }
            }
            .addOnFailureListener { it ->
                response = Resource.Error(it.message.toString())
            }

        return response
    }
}