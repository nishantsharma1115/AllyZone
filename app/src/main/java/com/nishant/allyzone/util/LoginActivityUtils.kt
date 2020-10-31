package com.nishant.allyzone.util

object LoginActivityUtils {

    fun validateCredentials(email: String, password: String): String {

        if (email.isEmpty()) {
            return Constants.EMAIL_EMPTY
        }

        if (password.isEmpty()) {
            return Constants.PASSWORD_EMPTY
        }

        if (!email.matches(Constants.emailRegex.toRegex())) {
            return Constants.EMAIL_INVALID
        }

        if (password.length < 8) {
            return Constants.PASSWORD_SMALL
        }

        return Constants.VALIDATE
    }
}