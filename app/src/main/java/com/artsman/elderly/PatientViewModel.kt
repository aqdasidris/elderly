package com.artsman.elderly

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PatientViewModel(val patientRepo: PatientInfoRepository){
    private var mCurrentState= MutableLiveData<State>()
    fun getState(): LiveData<State> {
       return mCurrentState
    }

    fun setAction(action: PAction) {
        if(action==PAction.start_action){
            mCurrentState.postValue(State.LoadingState)
            val data: String= patientRepo.fetchPatient()
            mCurrentState.postValue(State.LoadedState(data))

        }

    }



}

sealed class State{
   object StartState: State()
   object LoadingState: State()

    data class LoadedState(val data:String): State()






}


enum class PAction{
    start_action

}
