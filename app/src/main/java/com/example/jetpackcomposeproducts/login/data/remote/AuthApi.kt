package com.example.jetpackcomposeproducts.login.data.remote

import com.example.jetpackcomposeproducts.core.common.ApiConstant.LOGIN
import com.example.jetpackcomposeproducts.login.data.remote.model.LoginRequestDTO
import com.example.jetpackcomposeproducts.login.data.remote.model.LoginResponseDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST(LOGIN)
    suspend fun login(@Body loginRequestDTO: LoginRequestDTO): LoginResponseDTO

}