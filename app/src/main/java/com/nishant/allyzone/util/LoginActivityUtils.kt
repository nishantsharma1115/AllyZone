package com.nishant.allyzone.util

object LoginActivityUtils {

    fun validateCredentials(email: String, password: String): String {

        val regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$"

        if (email.isEmpty()) {
            return "EMAIL_EMPTY"
        }

        if (password.isEmpty()) {
            return "PASSWORD_EMPTY"
        }

        if (!email.matches(regex.toRegex())) {
            return "EMAIL_INVALID"
        }

        if (password.length < 8) {
            return "PASSWORD_SMALL"
        }

        return "VALIDATE"
    }
}