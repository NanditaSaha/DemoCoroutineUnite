package com.example.democoroutineunite.appdata

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.example.democoroutineunite.di.netModule
import com.example.democoroutineunite.di.repositoryModule
import com.example.democoroutineunite.di.retrofitServiceModule
import com.example.democoroutineunite.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        instance=this
        startKoin {
            androidContext(this@App)
            modules(viewModelModule)
            modules(netModule)
            modules(repositoryModule)
            modules(retrofitServiceModule)
        }
    }
    companion object Singleton{
        @get:Synchronized
        lateinit var instance:App
        private set
    }
}
