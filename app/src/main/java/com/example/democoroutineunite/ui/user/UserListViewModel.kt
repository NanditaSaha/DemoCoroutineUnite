package com.example.democoroutineunite.ui.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.democoroutineunite.data.model.UserResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


class UserListViewModel(
    private val userListRepository: UserListRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _data = MutableLiveData<UserResponse>()
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    val data: LiveData<UserResponse>
        get() = _data


    fun doGetUserList(page: String) {
        viewModelScope.launch(dispatcher) {
            loading.postValue(true)
            try {
                val response = userListRepository.getUserList(page)
                if (response.isSuccessful) {
                    println("DATA::${response.body().toString()}")
                    _data.postValue(response.body())
                    loading.postValue(true)
                }
            } catch (e: Exception) {
                loading.postValue(false)
            }
        }
    }
}