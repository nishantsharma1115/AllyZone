package com.nishant.allyzone.util

class Constants {

    companion object {

        //LoginActivityUtils
        const val emailRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$"
        const val EMAIL_EMPTY = "EMAIL_EMPTY"
        const val PASSWORD_EMPTY = "PASSWORD_EMPTY"
        const val EMAIL_INVALID = "EMAIL_INVALID"
        const val PASSWORD_SMALL = "PASSWORD_SMALL"
        const val VALIDATE = "VALIDATE"

        //LoginActivity
        const val LOGIN = "Login"
        const val NO_EMAIL_FOUND = "There is no user record corresponding to this identifier"
        const val INVALID_PASSWORD = "The password is invalid"
        const val CAN_NOT_BE_EMPTY = "Can not be Empty"
        const val INVALID_EMAIL_ADDRESS = "Invalid Email Address"
    }
}