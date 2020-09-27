package com.artsman.elderly

import android.util.Log

interface IRegisterationRepository {
    fun saveRegistration(registrationData: RegistrationData)
}

class RegistrationRepo: IRegisterationRepository{
    override fun saveRegistration(registrationData: RegistrationData) {
        Log.d("REPO", registrationData.toString())
    }

}
