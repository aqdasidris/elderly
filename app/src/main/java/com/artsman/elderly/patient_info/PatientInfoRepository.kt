package com.artsman.elderly.patient_info

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.artsman.elderly.patient_info.api.PatientInfoAPI
import com.google.gson.Gson

interface PatientInfoRepository {
   // fun fetchPatient(): PatientInfo
    fun getPatientList(): List<PatientItem>
    suspend fun fetchPatientRemote(): PatientInfo?

}


data class PatientInfo(
    val id: String,
    val name: String,
    val guardianCode: String,
    val contact_info: Contact,
    val emergency_contacts: List<EmergencyContact>,
    val bio: Bio,
    val address: AddressInfo,
)


data class Contact(
    val mobile_number: String = "",
    val email: String = "",
    val land_line: String = ""

)

data class EmergencyContact(val name: String, val contact_number: String = "")

data class Bio(
    val age: String,
    val weight: String,
    val medical_conditions: List<String>
)

data class AddressInfo(
    val line_1: String = "",
    val line_2: String = "",
    val district: String = "",
    val city: String = "",
    val pincode: String = ""
)

data class PatientItem(val name: String, val photoUrl: String)


//fake Repository getting data from assets

class AssetPatientRepository(val context: Context, val patientInfoAPI: PatientInfoAPI) :
    PatientInfoRepository {
   /* override fun fetchPatient(): PatientInfo {
        val read = readFromAsset()
        val dataObj = parseData(read)
        return dataObj
    }*/

    private fun parseData(read: String): PatientInfo {
        val gson = Gson()
        val dataObj = gson.fromJson(read, PatientInfo::class.java)
        return dataObj
    }

    override fun getPatientList(): List<PatientItem> {
        val data = parseData(readFromAsset())
        val list = listOf<PatientInfo>(data, data, data).map {
            PatientItem(it.name, "url")
        }
        return list;
    }

    override suspend fun fetchPatientRemote(): PatientInfo? {
        val response = patientInfoAPI.getPatients()?.execute()
        if (response?.isSuccessful == true) {
            Log.d("API", response.body().toString())
        }else {
            Log.d("API", "Failed: ${response?.errorBody()}")
        }
        return response?.body()?.data
    }

    private fun readFromAsset(): String {
        val data = context.assets.open("patient_info.txt").bufferedReader().use {
            it.readText()
        }
        return data
    }

}

