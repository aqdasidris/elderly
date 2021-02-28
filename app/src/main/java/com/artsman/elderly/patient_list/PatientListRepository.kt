package com.artsman.elderly.patient_list

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.artsman.elderly.care_taker.repo.DBEvent
import com.artsman.elderly.patient_list.repo.AbstractPatient.Companion.toDBPatient
import com.artsman.elderly.patient_list.repo.DBPatient
import com.artsman.elderly.patient_list.repo.DBPatient.Companion.toUIPatient
import com.artsman.elderly.patient_list.repo.PatientLocalProvider
import com.artsman.elderly.patient_list.repo.PatientRemoteProvider
import com.artsman.elderly.patient_list.repo.UIPatient
import com.google.gson.Gson

interface PatientListRepository {
    // fun fetchPatient(): PatientInfo
    fun getPatientLists(): List<PatientListItem>
    suspend fun fetchPatientListFromRemote(): List<PatientListItem>?
    fun fetchPateintListFromLocal():LiveData<List<UIPatient>>
    suspend fun fetchPatientFromRemote()


}



data class PatientListItem(val name: Int, val photoUrl:String)


//fake Repository getting data from assets

class AssetPatientListRepository(val context: Context, val remote: PatientRemoteProvider, val local:PatientLocalProvider) :
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

    override suspend fun fetchPatientListFromRemote(): List<PatientListItem>? {
        val response = remote.getPatientList()?.execute()
        if (response?.isSuccessful == true) {
            Log.d("API", response.body().toString())

        }else {
            Log.d("API", "Failed: ${response?.errorBody()}")
        }
        return response?.body()?.data
    }

    override fun fetchPateintListFromLocal(): LiveData<List<UIPatient>> {
        val fromDB:LiveData<List<DBPatient>>?=local.databaseProvider.getDatabase()?.getPatientDao()?.getAllPatients()
        val bridge=Transformations.map(fromDB as LiveData<List<DBPatient>>) {
            return@map it.map{
                it.toUIPatient()
            }
        }
        return bridge
    }

    override suspend fun fetchPatientFromRemote() {
        val response = remote.getPatientsV2()?.execute()
        if (response?.isSuccessful == true) {
            //Log.d("API", response.body().toString())
                response?.body()?.data?.forEach(){
                    val DBpatient=it.toDBPatient()
                    Log.d("PatientV2",DBPatient.toString())
                    local.addPatients(DBpatient)
                }
        }else {
            Log.d("API", "Failed: ${response?.errorBody()}")
        }


    }

    private fun readFromAsset(): String {
        val data = context.assets.open("patient_info.json").bufferedReader().use {
            it.readText()
        }

        return data
    }

}


