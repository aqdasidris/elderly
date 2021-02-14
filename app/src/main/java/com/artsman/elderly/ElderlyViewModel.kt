package com.artsman.elderly

import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.artsman.elderly.auth.GuardianCode
import com.artsman.elderly.auth.IRegisterationRepository
import com.artsman.elderly.auth.PatientData
import com.artsman.elderly.auth.RegistrationData
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ElderlyViewModel(val repository: IRegisterationRepository):ViewModel(){
    private var mCurrentStates= MutableLiveData<States>()

    fun getState(): LiveData <States> {
        mCurrentStates.postValue(States.user_type_state)
        return mCurrentStates

    }

    fun setAction(action: Action) {
        when (action) {
            Action.GuardianButtonAction -> {
                mCurrentStates.postValue(States.guardian_registration_personal_info_state)
            }
            Action.NameEmailNextAction -> {
                mCurrentStates.postValue(States.adding_guardian_contact_info_state)
            }
            is Action.LogIn -> {
                GlobalScope.launch(Dispatchers.IO) {
                    val data = repository.authUser(action.username, action.password)
                    data?.let {
                        GlobalScope.launch(Dispatchers.Main) {
                            mCurrentStates.postValue(States.redirect_to_home_state)
                        }
                    } ?: mCurrentStates.postValue(States.signin_failed_state)


                }
            }
            Action.LoginIntentAction->{
                mCurrentStates.postValue(States.log_in_state)
            }
            Action.ProceedToPasswordLoginAction -> {
                mCurrentStates.postValue(States.guardian_login_state)
                repository.saveRegistration(getRegistrationData())
            }
            Action.PatientUserTypeAction -> {
                mCurrentStates.postValue(States.patient_login_state)

            }
            Action.AddGuardianAction -> {
                mCurrentStates.postValue(States.add_guardian_state)
                repository.getPatientMail(getPatientMail())
            }
            Action.SignUpAction -> {
                repository.getGuardianCreds(getGuardianCreds())
            }
            Action.DoneAction -> {
                mCurrentStates.postValue(States.user_type_state)
                repository.getGuardianCode(getGuardianCode())
            }
            Action.BackAction -> {
                if(mCurrentStates.value==States.guardian_registration_personal_info_state){
                    mCurrentStates.postValue(States.user_type_state)
                }
                if(mCurrentStates.value==States.log_in_state){
                    mCurrentStates.postValue(States.guardian_registration_personal_info_state)
                } else if(mCurrentStates.value==States.adding_guardian_contact_info_state){
                    mCurrentStates.postValue(States.guardian_registration_personal_info_state)
                } else if(mCurrentStates.value==States.guardian_login_state){
                    mCurrentStates.postValue(States.adding_guardian_contact_info_state)
                } else if(mCurrentStates.value==States.patient_login_state){
                    mCurrentStates.postValue(States.user_type_state)
                } else if(mCurrentStates.value==States.add_guardian_state){
                    mCurrentStates.postValue(States.patient_login_state)
                } else if(mCurrentStates.value==States.user_type_state){
                    mCurrentStates.postValue(States.kill_state)
                }

            }
        }
    }

    private fun getRegistrationData(): RegistrationData {
        return RegistrationData(name = registrationMap["name"] ?: "",
                phone = registrationMap["phone"]?: "",
                email = registrationMap["email"] ?: "",
                address = registrationMap["address"] ?: "",
                pincode= registrationMap["pincode"]?: ""

            )
    }
    private fun  getGuardianCode(): GuardianCode {
        return GuardianCode(guardiancode = registrationMap["guardiancode"] ?: "",)
    }

    val registrationMap= mutableMapOf<String, String>()
    fun putValue(key: String, value: String) {
        registrationMap.put(key, value)
    }

    private fun getGuardianCreds(): GuardianCreds{
        return GuardianCreds(email= registrationMap["email"] ?: "",
        setPassword =registrationMap["setpassword"] ?: "",
        confirmPassword = registrationMap["confirmpassword"] ?: "")
    }
    private fun getPatientMail(): PatientData {
        return PatientData(mail = registrationMap["mail"]?: "")
    }
    



    companion object{
        const val DATA_NAME="name"
        const val DATA_EMAIL="email"
        const val GUARDIAN_CODE="guardiancode"
        const val SETPASSWORD="setpassword"
        const val CONFIRMPASSWORD="confirmpassword"
        const val PHONE="phone"
        const val PINCODE="pincode"
        const val ADDRESS="address"
        const val MAIL="mail"
    }
}
enum class States{
    user_type_state,
    guardian_registration_personal_info_state,
    adding_guardian_contact_info_state,
    guardian_login_state,
    patient_login_state,
    add_guardian_state,
    kill_state,
    log_in_state,
    redirect_to_home_state,
    signin_failed_state
}


sealed class Action{
    object GuardianButtonAction: Action()
    object NameEmailNextAction: Action()
    object ProceedToPasswordLoginAction: Action()
    object PatientUserTypeAction: Action()
    object AddGuardianAction: Action()
    object SignUpAction: Action()
    object BackAction: Action()
    object DoneAction: Action()
    object LoginIntentAction: Action()
    data class LogIn(val username: String, val password: String): Action()



}