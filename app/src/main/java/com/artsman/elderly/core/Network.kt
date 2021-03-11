package com.artsman.elderly.core

import android.content.Context
import android.util.Log
import com.artsman.elderly.care_taker.repo.ISupportedEvent
import com.artsman.elderly.care_taker.repo.StepInfo
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.InstanceCreator
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type


const val BASE_URL = "http://192.168.1.13:3000/"

/***
 * Todo This must be a singleton
 */
fun getRetrofitInstance(): Retrofit? {
    return Retrofit.Builder().apply {
        this.baseUrl(BASE_URL)
        this.addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                  /*  .registerTypeAdapter(StepInfo::class.java, EventInstanceCreator())*/
                    .create()
            )
        )
        this.client(OkHttpClient())
    }.build()
}
/***
 * Todo This must be a singleton
 */
fun getRetrofitInstance(context: Context): Retrofit? {
    return Retrofit.Builder().apply {
        this.baseUrl(BASE_URL)
        this.addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                  /*  .registerTypeAdapter(StepInfo::class.java, EventInstanceCreator())*/
                    .create()
            )
        )
        this.client(OkHttpClient.Builder().addInterceptor(ChuckInterceptor(context)).build())
    }.build()
}
