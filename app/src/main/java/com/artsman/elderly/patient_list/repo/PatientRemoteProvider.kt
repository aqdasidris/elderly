package com.artsman.elderly.patient_list.repo

import com.artsman.elderly.GenericData
import com.artsman.elderly.core.getRetrofitInstance
import com.artsman.elderly.patient_list.PatientListItem
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET

class PatientRemoteProvider (private val retrofit: Retrofit?= getRetrofitInstance() ) {



        private val api = retrofit?.create(IPatientListAPI::class.java)

        fun getPatientList(): Call<GenericData<List<PatientListItem>>>? {
            return api?.getPatients()
        }
        fun getPatientsV2():Call<GenericData<List<AbstractPatient<Map<String, Any>>>>>?{
            return api?.getPatientsV2()
        }


        interface IPatientListAPI {
            @GET("/sample/patient")
            fun getPatients(): Call<GenericData<List<PatientListItem>>>
            @GET("/sample/patient")
            fun getPatientsV2():Call<GenericData<List<AbstractPatient<Map<String,Any>>>>>?
        }

    }
