package com.nishant.allyzone.modals

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val userId: String = " ",
    var username: String = " ",
    var profilePicture: String? = null,
    val noOfAlly: Int = 0,
    val noOfPosts: Int = 0,
    var name: String = " ",
    var bio: String? = null
) : Parcelable