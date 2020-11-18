package com.artsman.elderly.patient_info

import android.content.Context
import com.google.gson.Gson

interface PatientInfoRepository {
    fun fetchPatient(): PatientInfo


}



data class PatientInfo(
    val id: String,
    val name: String,
    val guardianCode: String,
    val contact_info: Contact,
    val emergency_contact: List<EmergencyContact>,
    val bio: Bio,
    val address: AddressInfo,
)


data class Contact(
    val mobile_number: String = "",
    val email: String = "",
    val land_line: String = ""

)

data class EmergencyContact(val name: String, val mobile: String = "")

data class Bio(
    val age: String,
    val weight: String,
    val medical_conditions: List<String>
)
data class AddressInfo(
    val line1: String="",
    val line2: String="",
    val district: String="",
    val city: String="",
    val pincode: String=""
)


//fake Repository getting data from assets

class AssetPatientRepository(val context:Context): PatientInfoRepository {
    override fun fetchPatient(): PatientInfo {
        val read=readFromAsset()
        val gson= Gson()
        val dataObj=gson.fromJson(read, PatientInfo::class.java)
        return dataObj
    }

    private fun readFromAsset(): String{
        val data= context.assets.open("patient_info.txt").bufferedReader().use {
            it.readText()
        }
        return data
    }

}

