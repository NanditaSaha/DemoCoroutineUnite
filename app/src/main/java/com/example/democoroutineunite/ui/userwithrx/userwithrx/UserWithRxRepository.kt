package com.example.democoroutineunite.ui.userwithrx.userwithrx

import com.example.democoroutineunite.data.api.ApiHelper
import com.example.democoroutineunite.data.model.UserResponse
import io.reactivex.Single
import retrofit2.Response

class UserWithRxRepository(private val apiHelper: ApiHelper) {
    fun getUserList(page:String): Single<Response<UserResponse>> {
        return apiHelper.getUserRx(page)
    }
}