package com.example.democoroutineunite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.democoroutineunite.data.api.ApiHelper
import com.example.democoroutineunite.data.model.Data
import com.example.democoroutineunite.data.model.Support
import com.example.democoroutineunite.data.model.UserResponse
import com.example.democoroutineunite.ui.user.UserListRepository
import com.example.democoroutineunite.ui.user.UserListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import retrofit2.Retrofit


@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class RetroCouroutineListViewHolderTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    lateinit var retrofit: Retrofit

    @Mock
    lateinit var apiService: ApiHelper
    @Mock
    lateinit var repository:UserListRepository

    private  var loading: Boolean = false

    lateinit var listViewModel:UserListViewModel

    private lateinit var response: Response<UserResponse>

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
    }

    val testDispatcher = TestCoroutineDispatcher()
    @Test
    fun `check list viewmodel to fetch  data correctly`() = testDispatcher.runBlockingTest{
        var data = Data(1,"george.bluth@reqres.in","George","Bluth","https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg")
        var ad = Support("http://statuscode.org/","StatusCode Weekly")
        var list = arrayListOf<Data>()
        list.add(data)
        var retroResponse = UserResponse(1,6,12,2,list,ad)

        response = Response.success(retroResponse)
        listViewModel = UserListViewModel(repository,testDispatcher)
        if (listViewModel != null) {

            if (repository != null) {
                Mockito.`when`(repository.getUserList("1")).thenReturn(response)
            }
        }

        listViewModel.doGetUserList("1")
        advanceTimeBy(15_000)
        println("Loading Val::::${listViewModel.loading.value}")
        Assert.assertEquals(true,listViewModel.loading.value)
        Assert.assertEquals(retroResponse,listViewModel.data.value)

    }

  /* @Test
    fun `check list viewmodel to fetch  data Incorrectly`() = testDispatcher.runBlockingTest{
        listViewModel = UserListViewModel(testDispatcher,apiService)
        val mockException: HttpException =  mock()
        if (retrofit != null) {
            if (apiService != null) {
                Mockito.`when`(apiService.fetchUsers(2)).thenThrow(mockException)
            }
        }
        listViewModel.fetchUserListInfo(2)
        Assert.assertEquals(false, listViewModel.fetchLoadStatus()?.value)
    }*/
}