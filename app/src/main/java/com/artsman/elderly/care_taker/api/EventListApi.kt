package com.artsman.elderly.care_taker.api

import com.artsman.elderly.GenericData
import com.artsman.elderly.care_taker.EventInfo
import com.artsman.elderly.core.getRetrofitInstance
import com.artsman.elderly.patient_info.PatientInfo
import com.artsman.elderly.patient_info.api.PatientInfoAPI
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET

class EventListApi(private val retrofit: Retrofit?= getRetrofitInstance() ) {
    private val api= retrofit?.create(IEventInfoAPI::class.java)

    fun getEvents(): Call<GenericData<List<EventInfo>>>? {
        return api?.getEvents()
    }



    interface IEventInfoAPI{
        @GET("/sample/eventlist")
        fun getEvents(): Call<GenericData<List<EventInfo>>>
    }

}