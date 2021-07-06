package com.example.democoroutineunite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.democoroutineunite.data.api.ApiHelper
import com.example.democoroutineunite.data.model.Data
import com.example.democoroutineunite.data.model.Support
import com.example.democoroutineunite.data.model.UserResponse
import com.example.democoroutineunite.ui.userwithrx.userwithrx.UserWithRxRepository
import com.example.democoroutineunite.ui.userwithrx.userwithrx.UserWithRxViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import retrofit2.Retrofit
import java.util.concurrent.Executor


@RunWith(MockitoJUnitRunner::class)
class RetroRxViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var retrofit: Retrofit

    @Mock
    lateinit var apiService: ApiHelper

    @Mock
    lateinit var repository: UserWithRxRepository

    @get:Rule
    val schedulers = RxImmediateSchedulerRule()

    fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }


    private var loading: Boolean = false

    lateinit var listViewModel: UserWithRxViewModel
    private lateinit var response: Single<Response<UserResponse>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun fetchRetroInfoTest_success() {
        var data = Data(
            1,
            "george.bluth@reqres.in",
            "George",
            "Bluth",
            "https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg"
        )
        var ad = Support("http://statuscode.org/", "StatusCode Weekly")
        var list = arrayListOf<Data>()
        list.add(data)
        var retroResponse = UserResponse(1, 6, 12, 2, list, ad)
        response = Single.just(Response.success(retroResponse))
        listViewModel = UserWithRxViewModel(repository)
        if (listViewModel != null) {
            if (apiService != null) {
                Mockito.`when`(apiService.getUserRx("1")).thenReturn(response)
                println("DATA::${retroResponse}")
            }
        }

        listViewModel.doGetUserWithRx("1")
        //  Assert.assertEquals(retroResponse,listViewModel.data.value)
        //  Assert.assertEquals(loading,listViewModel.loading.value)
    }

     @Before
     fun setUpRXSchedulers(){
         listViewModel = UserWithRxViewModel(repository)
         var immediateThinScheduler  = object: Scheduler(){

             override fun createWorker(): Worker {
                 return  ExecutorScheduler.ExecutorWorker(Executor { it.run() },true)
             }
         }
         RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediateThinScheduler }
         RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediateThinScheduler }
         RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediateThinScheduler }
         RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediateThinScheduler }
         RxJavaPlugins.setInitNewThreadSchedulerHandler { _ -> immediateThinScheduler }
         RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> immediateThinScheduler }
         RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
         RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
         RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
         RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }


     }

}