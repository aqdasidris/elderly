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
        mCurrentState.postValue(State.LoadedStateData(data))
        mCurrentState.postValue(State.LoadedState)
    }




}

sealed class State{


   object LoadingState: State()
    object LoadedState:State()
    data class LoadedStateData(val data: PatientInfo): State()






}


enum class PAction{
    start_action,


}
