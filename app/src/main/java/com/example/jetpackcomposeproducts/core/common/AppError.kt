package com.example.jetpackcomposeproducts.core.common
sealed interface AppError {
    data object NoInternet : AppError
    data class Server(val code: Int) : AppError
    data object Unknown : AppError
}
