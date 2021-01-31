package com.artsman.elderly.patient_list.PatientListAPI

import com.artsman.elderly.GenericData
import com.artsman.elderly.core.getRetrofitInstance



import com.artsman.elderly.patient_list.PatientListItem
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET


class PatientListApi(private val retrofit: Retrofit?= getRetrofitInstance() ) {
    private val api = retrofit?.create(IPatientListAPI::class.java)

    fun getPatientList(): Call<GenericData<List<PatientListItem>>>? {
        return api?.getEvents()
    }


    interface IPatientListAPI {
        @GET("/sample/patient")
        fun getEvents(): Call<GenericData<List<PatientListItem>>>
    }

}