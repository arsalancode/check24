package com.check24.app.base.di

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import androidx.core.app.ActivityCompat
import com.check24.app.networking.domain.InternetHelper
import com.check24.app.networking.networking.repo.RepoService
import com.check24.app.networking.networking.repo.SearchRepository
import com.check24.app.networking.networking.repo.SearchRepositoryImpl
import com.check24.app.networking.provider.RetrofitBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class NetworkingAbstractModule {

    @Binds
    abstract fun provideSearchRepository(searchRepository: SearchRepositoryImpl): SearchRepository

}