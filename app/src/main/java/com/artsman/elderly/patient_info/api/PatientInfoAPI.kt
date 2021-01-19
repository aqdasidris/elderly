package com.artsman.elderly.patient_info.api

import com.artsman.elderly.core.getRetrofitInstance
import com.artsman.elderly.patient_info.PatientInfo
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers

class PatientInfoAPI(private val retrofit: Retrofit?= getRetrofitInstance()) {
    private val api= retrofit?.create(IPatientInfoAPI::class.java)

    fun getPatients(): Call<PatientInfo>? {
        return api?.getPatients()
    }



    interface IPatientInfoAPI{
        @GET("/sample/patient")
        fun getPatients(): Call<PatientInfo>
    }
}