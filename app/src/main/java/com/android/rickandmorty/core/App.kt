package com.android.rickandmorty.core

import android.app.Application
import com.android.rickandmorty.core.di.daoModule
import com.android.rickandmorty.core.di.localDatabaseModule
import com.android.rickandmorty.core.di.pagingDataSourceModule
import com.android.rickandmorty.core.di.remoteSourceModule
import com.android.rickandmorty.core.di.repositoryModule
import com.android.rickandmorty.core.di.retrofitModule
import com.android.rickandmorty.core.di.useCaseModule
import com.android.rickandmorty.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                retrofitModule,
                localDatabaseModule,
                remoteSourceModule,
                repositoryModule,
                daoModule,
                pagingDataSourceModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}