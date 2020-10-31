package com.nishant.allyzone.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class LoginActivityUtilsTest {

    @Test
    fun `empty email return EMAIL_EMPTY`() {
        val result = LoginActivityUtils.validateCredentials("", "123456789")
        assertThat(result).isEqualTo("EMAIL_EMPTY")
    }

    @Test
    fun `empty password return PASSWORD_EMPTY`() {
        val result = LoginActivityUtils.validateCredentials("official.nishant1115@gmail.com", "")
        assertThat(result).isEqualTo("PASSWORD_EMPTY")
    }

    @Test
    fun `invalid email format return EMAIL_INVALID`() {
        val result = LoginActivityUtils.validateCredentials("abcd", "123456789")
        assertThat(result).isEqualTo("EMAIL_INVALID")
    }

    @Test
    fun `password less than 8 return PASSWORD_SMALL`() {
        val result = LoginActivityUtils.validateCredentials("official.nishant1115@gmail.com", "123456")
        assertThat(result).isEqualTo("PASSWORD_SMALL")
    }

    @Test
    fun `correct email and password return VALIDATE`() {
        val result = LoginActivityUtils.validateCredentials("official.nishant1115@gmail.com", "123456789")
        assertThat(result).isEqualTo("VALIDATE")
    }
}