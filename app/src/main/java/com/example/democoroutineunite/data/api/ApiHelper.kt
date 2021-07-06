package com.example.democoroutineunite.data.api

import com.example.democoroutineunite.data.model.UserResponse
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiHelper {
    @GET("users")
    suspend fun getUser(@Query("page") page: String): Response<UserResponse>

    @GET("users")
    fun getUserRx(@Query("page") page: String): Single<Response<UserResponse>>

    @GET("users")
    suspend fun getUserFlow(@Query("page") page: String): Flow<UserResponse>

}