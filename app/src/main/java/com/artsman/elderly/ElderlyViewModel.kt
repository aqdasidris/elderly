package com.artsman.elderly

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData



class ElderlyViewModel(val repository: IRegisterationRepository){
    private var mCurrentStates= MutableLiveData<States>()
    fun getState(): LiveData <States> {
        mCurrentStates.postValue(States.choose_user_state)
        return mCurrentStates

    }

    fun setAction(action: Action) {
         if(action== Action.guardian_button_action){
             mCurrentStates.postValue(States.guardian_registration_name_email_state)
         }
        else if(action==Action.name_email_next_action){
             mCurrentStates.postValue(States.adding_guardian_address_contact_state)
         }
        else if(action==Action.proceed_to_password_login_action){
             mCurrentStates.postValue(States.guardian_login_state)
             repository.saveRegistration(getRegistrationData())
         }
        else if(action==Action.patient_user_type_action){
             mCurrentStates.postValue(States.patient_login_states)

         }
        else if(action==Action.add_guardian_action){
             mCurrentStates.postValue(States.add_guardian_state)
             repository.getPatientMail(getPatientMail())
         }
         else if(action==Action.log_in_action){
            mCurrentStates.postValue(States.add_event_state)
             repository.getGuardianCreds(getGuardianCreds())
         }
        else if(action==Action.add_event_action){
             mCurrentStates.postValue(States.choose_user_state)
         }
        else if(action==Action.done_action){
             mCurrentStates.postValue(States.choose_user_state)
             repository.getGuardianCode(getGuardianCode())
         }
        else if(action==Action.back_action){
             if(mCurrentStates.value==States.guardian_registration_name_email_state){
                 mCurrentStates.postValue(States.choose_user_state)
             }
             else if(mCurrentStates.value==States.adding_guardian_address_contact_state){
                 mCurrentStates.postValue(States.guardian_registration_name_email_state)
             }
             else if(mCurrentStates.value==States.guardian_login_state){
                 mCurrentStates.postValue(States.adding_guardian_address_contact_state)
             }
             else if(mCurrentStates.value==States.patient_login_states){
                 mCurrentStates.postValue(States.choose_user_state)
             }
             else if(mCurrentStates.value==States.add_guardian_state){
                 mCurrentStates.postValue(States.patient_login_states)
             }
             else if(mCurrentStates.value==States.add_event_state){
                 mCurrentStates.postValue(States.guardian_login_state)
             }
             else if(mCurrentStates.value==States.choose_user_state){
                 mCurrentStates.postValue(States.kill_state)
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
    private fun  getGuardianCode(): GuardianCode{
        return GuardianCode(guardiancode = registrationMap["guardiancode"] ?: "",)
    }

    val registrationMap= mutableMapOf<String, String>()
    fun putValue(key: String, value: String) {
        registrationMap.put(key, value)
    }

    private fun getGuardianCreds(): guardianLogin{
        return guardianLogin(email= registrationMap["email"] ?: "",
        setpassword =registrationMap["setpassword"] ?: "",
        confirmpassword = registrationMap["confirmpassword"] ?: "")
    }
    private fun getPatientMail(): getPatientMail{
        return getPatientMail(mail = registrationMap["mail"]?: "")
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
    choose_user_state,
    guardian_registration_name_email_state,
    adding_guardian_address_contact_state,
    guardian_login_state,
    patient_login_states,
    add_guardian_state,
    kill_state,
    add_event_state,

}
enum class Action{
    guardian_button_action,
    name_email_next_action,
    proceed_to_password_login_action,
    patient_user_type_action,
    add_guardian_action,
    log_in_action,
    back_action,
    add_event_action,
    done_action

}