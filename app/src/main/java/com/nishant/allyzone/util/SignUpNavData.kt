package com.nishant.allyzone.util

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class SignUpNavData : Parcelable {
    var email: String = ""
    var fullName: String = ""
    var password: String = ""
    var dob: String = ""
    var username: String = ""
    var userId: String = ""
}