package com.nishant.allyzone.repositories

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nishant.allyzone.modals.User

class DataRepository {

    private val db = Firebase.firestore

    fun registerUser(
        user: User,
        onCompleteCallback: (Task<Void>) -> Unit,
        onFailureCallback: (Exception) -> Unit
    ) {
        db.collection("Users")
            .document(user.userId)
            .set(user)
            .addOnCompleteListener { task ->
                onCompleteCallback(task)
            }
            .addOnFailureListener { exception ->
                onFailureCallback(exception)
            }
    }

    fun getUserData(
        userId: String,
        onSuccessCallback: (DocumentSnapshot) -> Unit,
        onFailureCallback: (Exception) -> Unit
    ) {
        db.collection("Users")
            .document(userId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                onSuccessCallback(documentSnapshot)
            }
            .addOnFailureListener { exception ->
                onFailureCallback(exception)
            }
    }

}