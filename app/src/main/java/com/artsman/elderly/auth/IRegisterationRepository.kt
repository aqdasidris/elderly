package com.artsman.elderly.auth

import android.util.Log
import com.artsman.elderly.data.IPreferenceHelper
import com.artsman.elderly.GuardianCreds
import com.artsman.elderly.auth.user_auth_api.UserAuthApi
import com.google.gson.Gson
import android.content.Context
import com.artsman.elderly.GenericData
import java.io.IOException
import java.lang.Exception
import kotlin.coroutines.coroutineContext

interface IRegisterationRepository {
    fun saveRegistration(registrationData: RegistrationData)
    fun getGuardianCode(GuardianCode: GuardianCode)
    fun getGuardianCreds(GuardianCreds: GuardianCreds)
    fun getPatientMail(PatientData: PatientData)
    suspend fun authUser(username: String, password: String):AuthUser?

}

typealias AuthUser = GenericData<String>

class RegistrationRepo constructor(val preferenceHelper: IPreferenceHelper, val userAuthApi: UserAuthApi):
    IRegisterationRepository {
    override fun saveRegistration(registrationData: RegistrationData) {
        Log.d("REPO", registrationData.toString())
        preferenceHelper.saveRegistration(registrationData)
    }

    override fun getGuardianCode(GuardianCode: GuardianCode) {
        Log.d("Guardiancode", GuardianCode.toString())
    }

    override fun getGuardianCreds(GuardianCreds: GuardianCreds) {
        Log.d("CREDENTIALS", GuardianCreds.toString())
    }

    override fun getPatientMail(PatientData: PatientData) {
        Log.d("Mail", PatientData.toString())
    }


    override suspend fun authUser(username: String, password: String): AuthUser? {
        try{
            val response = userAuthApi.userAuth(username, password)?.execute()
            response?.let {
                if(it.isSuccessful) return it.body()?.data
            }
        }catch (e: IOException){}
        return null
    }




}


