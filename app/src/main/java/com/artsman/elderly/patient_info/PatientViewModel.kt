package com.artsman.elderly.patient_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PatientViewModel(val patientRepo: PatientInfoRepository){
    private var mCurrentState= MutableLiveData<State>()
    fun getState(): LiveData<State> {
       return mCurrentState
    }
    fun setAction(action: PAction){
        mCurrentState.postValue(State.LoadingState)
        val data: PatientInfo = patientRepo.fetchPatient()
        mCurrentState.postValue(State.LoadedState(data))
    }




}

sealed class State{


   object LoadingState: State()
    data class LoadedState(val data: PatientInfo): State()






}


enum class PAction{
    start_action,


}
