package com.nishant.allyzone.repositories

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.nishant.allyzone.util.SignUpNavData

class AuthRepository {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signUpUser(
        user: SignUpNavData,
        callback: (Task<AuthResult>) -> Unit
    ) {
        mAuth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                callback(task)
            }
    }

    fun loginUser(
        email: String,
        password: String,
        completeCallback: (Task<AuthResult>) -> Unit,
        failureCallback: (Exception) -> Unit
    ) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                completeCallback(task)
            }
            .addOnFailureListener {
                failureCallback(it)
            }
    }
}