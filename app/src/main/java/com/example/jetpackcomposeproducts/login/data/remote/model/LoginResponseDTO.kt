package com.example.jetpackcomposeproducts.login.data.remote.model

data class LoginResponseDTO(
    val accessToken: String,
    val refreshToken: String,
    val id: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val image: String
)