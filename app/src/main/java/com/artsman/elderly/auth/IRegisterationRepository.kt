package com.artsman.elderly.auth

import android.util.Log
import com.artsman.elderly.data.IPreferenceHelper
import com.artsman.elderly.GuardianCreds

interface IRegisterationRepository {
    fun saveRegistration(registrationData: RegistrationData)
    fun getGuardianCode(GuardianCode: GuardianCode)
    fun getGuardianCreds(GuardianCreds: GuardianCreds)
    fun getPatientMail(PatientData: PatientData)
}

class RegistrationRepo constructor(val preferenceHelper: IPreferenceHelper):
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

}
