package com.example.jetpackcomposeproducts.login.domain

import com.example.jetpackcomposeproducts.core.common.Resource
import com.example.jetpackcomposeproducts.login.data.remote.model.LoginRequestDTO
import com.example.jetpackcomposeproducts.login.data.remote.model.LoginResponseDTO
import com.example.jetpackcomposeproducts.login.ui.LoginUiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(val loginRepository: LoginRepository) {

    suspend operator fun invoke(loginRequestDTO: LoginRequestDTO) :Flow<Resource<LoginResponseDTO>>{
       return loginRepository.login(loginRequestDTO)
    }
}