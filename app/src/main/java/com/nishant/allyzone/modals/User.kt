package com.nishant.allyzone.modals

data class User(
    val userId: String,
    val username: String,
    val profilePicture: String? = null,
    val noOfAlly: Int,
    val noOfPosts: Int,
    val name: String,
    val bio: String? = null
)