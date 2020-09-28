package com.artsman.elderly

import android.util.Log

interface IRegisterationRepository {
    fun saveRegistration(registrationData: RegistrationData)
    fun getGuardianCode(GuardianCode: GuardianCode)
    fun getGuardianCreds(guardianLogin: guardianLogin)
}

class RegistrationRepo: IRegisterationRepository{
    override fun saveRegistration(registrationData: RegistrationData) {
        Log.d("REPO", registrationData.toString())
    }

    override fun getGuardianCode(GuardianCode: GuardianCode) {
        Log.d("Guardiancode", GuardianCode.toString())
    }

    override fun getGuardianCreds(guardianLogin: guardianLogin) {
        Log.d("CREDENTIALS", guardianLogin.toString())
    }

}
