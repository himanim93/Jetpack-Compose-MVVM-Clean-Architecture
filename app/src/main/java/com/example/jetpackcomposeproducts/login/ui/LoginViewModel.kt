package com.example.jetpackcomposeproducts.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposeproducts.R
import com.example.jetpackcomposeproducts.core.common.AppError
import com.example.jetpackcomposeproducts.core.common.Resource
import com.example.jetpackcomposeproducts.login.data.remote.model.LoginRequestDTO
import com.example.jetpackcomposeproducts.login.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Ideal)
    val loginUiState = _loginUiState.asStateFlow()

    private val _loginUiEvent = MutableSharedFlow<LoginUiEvent>()
    val loginUiEvent = _loginUiEvent.asSharedFlow()

    fun login(loginRequestDTO: LoginRequestDTO) {
        val isValid = validateLogin(loginRequestDTO.username, loginRequestDTO.password)
        if (isValid) {
            viewModelScope.launch {
                _loginUiState.value = LoginUiState.Loading

                loginUseCase.invoke(loginRequestDTO).collect {
                    when (it) {
                        is Resource.Loading -> _loginUiState.value = LoginUiState.Loading
                        is Resource.Success -> {
                            _loginUiState.value = LoginUiState.Success(it.data)
                            _loginUiEvent.emit(LoginUiEvent.ShowToast(R.string.login_success))
                            _loginUiEvent.emit(LoginUiEvent.NavigateToProduct)
                        }

                        is Resource.Error -> {
                            _loginUiState.value =
                                LoginUiState.Error(it.error.toMessageRes())
                            _loginUiEvent.emit(LoginUiEvent.ShowToast(it.error.toMessageRes()))
                        }
                    }
                }
            }
        }
    }

    private fun validateLogin(username: String, password: String): Boolean {
        val usernameError = when {
            username.isBlank() -> R.string.login_error_username_empty
            else -> -1
        }

        val passwordError = when {
            password.isBlank() -> R.string.login_error_password_empty
            password.length < 6 -> R.string.login_error_password_invalid
            else -> -1
        }

        val isUsernameValid = usernameError == -1
        val isPasswordValid = passwordError == -1

        _loginUiState.value = LoginUiState.LoginValidationError(
            isUserNameValid = isUsernameValid,
            usernameMessage = usernameError,
            isPasswordValid = isPasswordValid,
            passwordMessage = passwordError
        )

        return isUsernameValid && isPasswordValid
    }

    private fun AppError.toMessageRes(): Int = when (this) {
        AppError.NoInternet -> R.string.error_no_internet
        is AppError.Server ->
            if (code == 400 || code == 401) R.string.error_invalid_credentials
            else R.string.error_server

        AppError.Unknown -> R.string.error_generic
    }
}