package com.nishant.allyzone.repositories

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.nishant.allyzone.modals.User

class DataRepository {

    private val db = Firebase.firestore
    private val storageRef = Firebase.storage.reference

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

    fun updateUserData(
        user: User,
        onCompleteCallback: (Task<Void>) -> Unit,
        onFailureCallback: (Exception) -> Unit
    ) {
        db.collection("Users")
            .document(user.userId)
            .set(user, SetOptions.merge())
            .addOnCompleteListener(onCompleteCallback)
            .addOnFailureListener(onFailureCallback)
    }

    fun uploadProfilePicture(
        userId: String,
        file: Uri,
        onSuccessCallback: (String) -> Unit,
        onFailureCallback: (Exception) -> Unit
    ) {
        storageRef.child("ProfilePicture").child(userId).putFile(file)
            .addOnSuccessListener { task ->
                if (task.metadata != null && task.metadata!!.reference != null) {
                    val result = task.storage.downloadUrl
                    result.addOnSuccessListener {
                        onSuccessCallback(it.toString())
                    }
                }
            }
            .addOnFailureListener(onFailureCallback)
    }
}