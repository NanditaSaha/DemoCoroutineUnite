package com.example.democoroutineunite.di

import android.app.Application
import com.example.democoroutineunite.data.api.ApiHelper
import com.example.democoroutineunite.ui.user.UserListRepository
import com.example.democoroutineunite.ui.user.UserListViewModel
import com.example.democoroutineunite.ui.userwithrx.userwithrx.UserWithRxRepository
import com.example.democoroutineunite.ui.userwithrx.userwithrx.UserWithRxViewModel
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.SupervisorJob

val netModule = module {
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideHttpClient(cache: Cache): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .cache(cache)
        return okHttpClientBuilder.build()
    }

    fun providegson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create(factory))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { providegson() }
    single { provideRetrofit(get(), get()) }
}

val retrofitServiceModule = module {
    fun provideUserService(retrofit: Retrofit): ApiHelper {
        return retrofit.create(ApiHelper::class.java)
    }

    single { provideUserService(get()) }
}

val viewModelModule= module {
    viewModel{ UserListViewModel(get(),get())}
    viewModel { UserWithRxViewModel(get()) }
   // factory { CoroutineScope(Dispatchers.IO) }
}

val repositoryModule= module {
    fun provideUserRepository(api:ApiHelper):UserListRepository{
        return UserListRepository(api)
    }
    fun provideUserWithRxRepository(api:ApiHelper):UserWithRxRepository{
        return UserWithRxRepository(api)
    }
    single { provideUserRepository(get()) }
    single { provideUserWithRxRepository(get()) }
}