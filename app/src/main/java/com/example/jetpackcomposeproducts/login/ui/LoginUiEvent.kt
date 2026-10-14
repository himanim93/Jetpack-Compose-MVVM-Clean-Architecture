package com.example.jetpackcomposeproducts.login.ui

import androidx.annotation.StringRes

sealed interface LoginUiEvent {
    data class ShowToast(@StringRes val message: Int) : LoginUiEvent
    data object NavigateToProduct : LoginUiEvent
}