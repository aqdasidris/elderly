package com.artsman.elderly

import android.util.Log
import com.artsman.elderly.data.IPreferenceHelper

interface IRegisterationRepository {
    fun saveRegistration(registrationData: RegistrationData)
    fun getGuardianCode(GuardianCode: GuardianCode)
    fun getGuardianCreds(guardianLogin: guardianLogin)
    fun getPatientMail(getPatientMail: getPatientMail)
}

class RegistrationRepo constructor(val preferenceHelper: IPreferenceHelper): IRegisterationRepository{
    override fun saveRegistration(registrationData: RegistrationData) {
        Log.d("REPO", registrationData.toString())
        preferenceHelper.saveRegistration(registrationData)
    }

    override fun getGuardianCode(GuardianCode: GuardianCode) {
        Log.d("Guardiancode", GuardianCode.toString())
    }

    override fun getGuardianCreds(guardianLogin: guardianLogin) {
        Log.d("CREDENTIALS", guardianLogin.toString())
    }

    override fun getPatientMail(getPatientMail: getPatientMail) {
        Log.d("Mail", getPatientMail.toString())
    }

}
