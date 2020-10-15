package com.artsman.elderly.data

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.artsman.elderly.GuardianCreds
import com.artsman.elderly.auth.GuardianCode
import com.artsman.elderly.auth.PatientData
import com.artsman.elderly.auth.RegistrationData
import com.google.gson.Gson

interface IPreferenceHelper{
    fun saveRegistration(registrationData: RegistrationData)
    fun getGuardianCreds(GuardianCreds: GuardianCreds)
    fun getPatientMail(PatientData: PatientData)
    fun getGuardianCode(GuardianCode: GuardianCode)


}


class AppPreference constructor(val context: Context): IPreferenceHelper{

    val sharedPref: SharedPreferences = (context as AppCompatActivity).getPreferences(Context.MODE_PRIVATE)
    private val gson=Gson()
    private val TAG="AppPreference"

    private val KEY_REGISTRATION_DATA="key_registration_data"
    private val KEY_GUARDIAN_LOGIN_DATA="key_guardian_login_data"
    private val KEY_PATIENT_MAIL="key_patient_mail"
    private val KEY_ADD_GUARDIAN="key_add_guardian"

    override fun saveRegistration(registrationData: RegistrationData) {
        val json=gson.toJson(registrationData)
        sharedPref.edit().putString(KEY_REGISTRATION_DATA, json).apply()
    }

    override fun getGuardianCreds(GuardianCreds: GuardianCreds) {
        val json=gson.toJson(GuardianCreds)
        sharedPref.edit().putString(KEY_GUARDIAN_LOGIN_DATA, json).apply()
    }

    override fun getPatientMail(PatientData: PatientData){
        val json=gson.toJson(PatientData)
        sharedPref.edit().putString(KEY_PATIENT_MAIL, json).apply()
    }

    override fun getGuardianCode(GuardianCode: GuardianCode) {
        val json=gson.toJson(GuardianCode)
        sharedPref.edit().putString(KEY_ADD_GUARDIAN, json).apply()
    }




}