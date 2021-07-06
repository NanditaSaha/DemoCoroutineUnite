package com.example.democoroutineunite.ui.user

import com.example.democoroutineunite.data.api.ApiHelper
import com.example.democoroutineunite.data.model.UserResponse
import retrofit2.Response

class UserListRepository(private val apiHelper: ApiHelper) {
    suspend fun getUserList(page:String):Response<UserResponse>{
        return apiHelper.getUser(page)
    }
}