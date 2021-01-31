package com.artsman.elderly.patient_list

import android.content.Context
import android.util.Log
import com.artsman.elderly.patient_list.PatientListAPI.PatientListApi
import com.google.gson.Gson
import com.squareup.picasso.Picasso

interface PatientListRepository {
    // fun fetchPatient(): PatientInfo
    fun getPatientLists(): List<PatientListItem>
    suspend fun fetchPatientListRemote(): List<PatientListItem>?

}



data class PatientListItem(val name: String, val photoUrl:String )


//fake Repository getting data from assets

class AssetPatientListRepository(val context: Context, val patientListAPI: PatientListApi) :
    PatientListRepository {
    /* override fun fetchPatient(): PatientInfo {
         val read = readFromAsset()
         val dataObj = parseData(read)
         return dataObj
     }*/

    private fun parseData(read: String): PatientListItem {
        val gson = Gson()
        val dataObj = gson.fromJson(read, PatientListItem::class.java)
        return dataObj
    }

    override fun getPatientLists(): List<PatientListItem> {
        val data = parseData(readFromAsset())
        val list = listOf<PatientListItem>(data, data, data).map {
            PatientListItem(it.name, "url")
        }
        return list;
    }

    override suspend fun fetchPatientListRemote(): List<PatientListItem>? {
        val response = patientListAPI.getPatientList()?.execute()
        if (response?.isSuccessful == true) {
            Log.d("API", response.body().toString())
        }else {
            Log.d("API", "Failed: ${response?.errorBody()}")
        }
        return response?.body()?.data
    }

    private fun readFromAsset(): String {
        val data = context.assets.open("patient_info.json").bufferedReader().use {
            it.readText()
        }

        return data
    }

}


