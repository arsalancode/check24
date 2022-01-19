package com.check24.app.networking.provider

import com.check24.app.networking.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitBuilder {

    val check24Instance by lazy {
        provideRetrofit(
            BuildConfig.LIVE_HOST
        )
    }

    private fun provideRetrofit(
        host: String,
        okHttpClient: OkHttpClient = provideOkHttpClient(),
        converterFactory: Converter.Factory = GsonConverterFactory.create()
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(host)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor())
            .build()
    }

}