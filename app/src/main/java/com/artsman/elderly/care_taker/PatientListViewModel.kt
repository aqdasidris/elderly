package com.artsman.elderly.care_taker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.artsman.elderly.patient_info.PatientInfoRepository

class PatientListViewModel(val repo:CareTakerEventRepository) {

    private val mState = MutableLiveData<States>()
    fun subscribe(): LiveData<States> {
        return mState
    }

    fun setAction(action: Actions) {
        when (action) {
            Actions.Start -> {
                mState.postValue(States.Loading)
                mState.postValue(States.Loaded(repo.getEventList()))
            }
        }
    }


    sealed class States {
        data class Loaded(val items: List<Event>) : States()

        object Loading : States()
    }

    sealed class Actions {
        object Start : Actions()
    }

}