package com.artsman.elderly.data

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.artsman.elderly.RegistrationData
import com.google.gson.Gson

interface IPreferenceHelper{
    fun saveRegistration(registrationData: RegistrationData)

}


class AppPreference constructor(val context: Context): IPreferenceHelper{

    val sharedPref: SharedPreferences = (context as AppCompatActivity).getPreferences(Context.MODE_PRIVATE)
    private val gson=Gson()
    private val TAG="AppPreference"

    private val KEY_REGISTRATION_DATA="key_registration_data"

    override fun saveRegistration(registrationData: RegistrationData) {
        val json=gson.toJson(registrationData)
        sharedPref.edit().putString(KEY_REGISTRATION_DATA, json).apply()
    }

}