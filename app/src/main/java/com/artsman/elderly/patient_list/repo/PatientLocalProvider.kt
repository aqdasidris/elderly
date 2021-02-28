package com.artsman.elderly.patient_list.repo

import com.artsman.elderly.core.DatabaseProvider

class PatientLocalProvider(val databaseProvider: DatabaseProvider) {
    fun addPatients(vararg patients:DBPatient){
        getPatientDao()?.insertAll(*patients)
    }
    fun getPatients(vararg patients:DBPatient){
        getPatientDao()?.getAllPatients()
    }

    private fun getPatientDao()=databaseProvider.getDatabase()?.getPatientDao()
}