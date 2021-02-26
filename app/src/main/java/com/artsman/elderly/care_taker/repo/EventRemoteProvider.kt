package com.artsman.elderly.care_taker.repo

import com.artsman.elderly.GenericData
import com.artsman.elderly.core.getRetrofitInstance
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET

class EventRemoteProvider(private val retrofit: Retrofit?= getRetrofitInstance() ) {
    private val api= retrofit?.create(IEventInfoAPI::class.java)

    fun getEvents(): Call<GenericData<List<EventInfo>>>? {
        return api?.getEvents()
    }

    fun getEventsV2(): Call<GenericData<List<AbstractEvent<Map<String, Any>>>>>? {
        return api?.getEventsV2()
    }



    interface IEventInfoAPI{
        @GET("/sample/eventlist")
        fun getEvents(): Call<GenericData<List<EventInfo>>>

        @GET("/sample/eventlistv2")
        fun getEventsV2(): Call<GenericData<List<AbstractEvent<Map<String, Any>>>>>
    }

}