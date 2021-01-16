package com.artsman.elderly.care_taker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.artsman.elderly.patient_info.PatientInfoRepository
import com.artsman.elderly.patient_info.PatientItem
import com.artsman.elderly.patient_info.State

class PatientListViewModel(val repo: PatientInfoRepository) {

    private val mState = MutableLiveData<States>()
    fun subscribe(): LiveData<States> {
        return mState
    }

    fun setAction(action: Actions) {
        when (action) {
            Actions.Start -> {
                mState.postValue(States.Loading)
                mState.postValue(States.Loaded(repo.getPatientList()))
            }
        }
    }


    sealed class States {
        data class Loaded(val items: List<PatientItem>) : States()

        object Loading : States()
    }

    sealed class Actions {
        object Start : Actions()
    }

}