package com.example.jetpackcomposeproducts.login.domain

import com.example.jetpackcomposeproducts.core.common.Resource
import com.example.jetpackcomposeproducts.login.data.remote.model.LoginRequestDTO
import com.example.jetpackcomposeproducts.login.data.remote.model.LoginResponseDTO
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun login( loginRequestDTO: LoginRequestDTO): Flow<Resource<LoginResponseDTO>>
}