package com.example.jetpackcomposeproducts.login.ui

import androidx.annotation.StringRes
import com.example.jetpackcomposeproducts.login.data.remote.model.LoginResponseDTO

sealed class LoginUiState {
    object Ideal : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val data: LoginResponseDTO) : LoginUiState()
    data class Error(@StringRes val message: Int) : LoginUiState()

    data class LoginValidationError(
        val isUserNameValid: Boolean,
        @StringRes val usernameMessage: Int,
        val isPasswordValid: Boolean,
        @StringRes val passwordMessage: Int
    ) : LoginUiState()
}