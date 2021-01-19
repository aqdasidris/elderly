package com.artsman.elderly.patient_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PatientViewModel(val patientRepo: PatientInfoRepository) : ViewModel() {
    private var mCurrentState = MutableLiveData<State>()
    fun getState(): LiveData<State> {
        return mCurrentState
    }

    fun setAction(action: PAction) {
        mCurrentState.postValue(State.LoadingState)
        GlobalScope.launch(Dispatchers.IO) {
            val data=patientRepo.fetchPatientRemote()
            data?.let {
                GlobalScope.launch(Dispatchers.Main) {
                    mCurrentState.postValue(State.LoadedState(it))
                }

            }
        }
    }


}

sealed class State {


    object LoadingState : State()
    data class LoadedState(val data: PatientInfo) : State()


}


enum class PAction {
    start_action,


}
