package com.example.jetpackcomposeproducts.login.data

import com.example.jetpackcomposeproducts.core.common.AppError
import com.example.jetpackcomposeproducts.core.common.Resource
import com.example.jetpackcomposeproducts.login.data.remote.AuthApi
import com.example.jetpackcomposeproducts.login.data.remote.model.LoginRequestDTO
import com.example.jetpackcomposeproducts.login.data.remote.model.LoginResponseDTO
import com.example.jetpackcomposeproducts.login.domain.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginRepositoryImp @Inject constructor(val authApi: AuthApi) : LoginRepository {

    override suspend fun login(
        loginRequestDTO: LoginRequestDTO
    ): Flow<Resource<LoginResponseDTO>> = flow {
        emit(Resource.Loading)
        emit(Resource.Success(authApi.login(loginRequestDTO)))
    }.catch {
        emit(Resource.Error(it.toLoginError()))
    }.flowOn(Dispatchers.IO)

    private fun Throwable.toLoginError() = when (this) {
        is IOException -> AppError.NoInternet
        is HttpException -> AppError.Server(code())
        else -> AppError.Unknown
    }
}