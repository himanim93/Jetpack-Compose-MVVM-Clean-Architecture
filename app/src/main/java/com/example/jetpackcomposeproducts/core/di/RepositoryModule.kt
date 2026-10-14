package com.example.jetpackcomposeproducts.core.di

import com.example.jetpackcomposeproducts.login.data.LoginRepositoryImp
import com.example.jetpackcomposeproducts.login.domain.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindLoginRepository(
        loginRepositoryImp: LoginRepositoryImp
    ): LoginRepository

}