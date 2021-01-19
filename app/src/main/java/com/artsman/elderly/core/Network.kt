package com.artsman.elderly.core

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL= "http://192.168.1.21:3000/"

/***
 * Todo This must be a singleton
 */
fun getRetrofitInstance(): Retrofit? {
    return Retrofit.Builder().apply {
        this.baseUrl(BASE_URL)
        this.addConverterFactory(GsonConverterFactory.create(Gson()))
        this.client(OkHttpClient())
    }.build()
}