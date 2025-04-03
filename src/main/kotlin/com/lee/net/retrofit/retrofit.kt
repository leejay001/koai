package com.lee.net.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

fun okHttpClient(
    callTimeSecond: Long = 120L,
    readTimeoutSeconds: Long = 120L,
    writeTimeoutSeconds: Long = 120L,
    block: ((OkHttpClient.Builder) -> OkHttpClient.Builder)? = null
) =
    OkHttpClient.Builder()
        .apply {
            block?.invoke(this)
        }
        .addInterceptor(HttpLoggingInterceptor())
        .callTimeout(callTimeSecond, TimeUnit.SECONDS)
        .readTimeout(readTimeoutSeconds, TimeUnit.SECONDS)
        .writeTimeout(writeTimeoutSeconds, TimeUnit.SECONDS)
        .build()

fun retrofit(
    baseUrl: String,
    callTimeSecond: Long = 120L,
    readTimeoutSeconds: Long = 120L,
    writeTimeoutSeconds: Long = 120L,
    retrofitBuilder: ((Retrofit.Builder) -> Retrofit.Builder)? = null,
    clientBuilder: ((OkHttpClient.Builder) -> OkHttpClient.Builder)? = null

): Retrofit =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient(callTimeSecond, readTimeoutSeconds, writeTimeoutSeconds, clientBuilder))
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .apply {
            retrofitBuilder?.invoke(this)
        }
        .build()

inline fun <reified T> Retrofit.service(): T = create(T::class.java)