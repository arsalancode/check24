package com.check24.app.networking.di

import android.content.Context
import android.net.ConnectivityManager
import com.check24.app.networking.BuildConfig
import com.check24.app.networking.domain.InternetHelper
import com.check24.app.networking.networking.repo.RepoService
import com.check24.app.networking.networking.repo.SearchRepositoryImpl
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Provides
    fun provideConnectivityManager(@ApplicationContext appContext: Context): ConnectivityManager {
        return appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    fun provideSearchRepositoryImpl(internetHelper: InternetHelper, retrofit: Retrofit): SearchRepositoryImpl {
        return SearchRepositoryImpl(
            internetHelper,
            retrofit.create(RepoService::class.java)
        )
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.LIVE_HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(provideLoggingInterceptor())
            .build()
    }

    @Provides
    fun provideLoggingInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

}