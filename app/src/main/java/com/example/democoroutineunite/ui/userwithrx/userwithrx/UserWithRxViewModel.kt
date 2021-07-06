package com.example.democoroutineunite.ui.userwithrx.userwithrx

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.democoroutineunite.data.model.UserResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class UserWithRxViewModel (
    private val userWithRxRepository: UserWithRxRepository
) : ViewModel(){
    private val _data = MutableLiveData<UserResponse>()
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    val data: LiveData<UserResponse>
        get() = _data
    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun doGetUserWithRx(page: String){
        println("DATA::NAND}")
        compositeDisposable.add(userWithRxRepository.getUserList(page)
                           .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object :DisposableSingleObserver<Response<UserResponse>>(){

                override fun onError(e: Throwable) {
                    loading.postValue(false)
                }

                override fun onSuccess(t: Response<UserResponse>) {
                    loading.postValue(true)
                    _data.postValue(t.body())
                    println("DATA::${t.body().toString()}")
                }

            }))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}