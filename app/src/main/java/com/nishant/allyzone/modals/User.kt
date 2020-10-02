package com.nishant.allyzone.modals

data class User(
    val userId: String = " ",
    val username: String = " ",
    val profilePicture: String? = null,
    val noOfAlly: Int = 0,
    val noOfPosts: Int = 0,
    val name: String = " ",
    val bio: String? = null
)