package com.artsman.elderly.patient_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.artsman.elderly.patient_list.repo.UIPatient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class PatientListViewModel(val repo: PatientListRepository): ViewModel() {

    private val mCurrentState = MediatorLiveData<States>()
    fun subscribe(): LiveData<States> {
        return mCurrentState
    }

    fun setAction(action: Actions) {
        mCurrentState.postValue(States.Loading)
        GlobalScope.launch(Dispatchers.IO) {
            repo.fetchPatientFromRemote()
        }
        mCurrentState.addSource(repo.fetchPateintListFromLocal()) { items ->
            mCurrentState.postValue(States.Loaded(items = items))
        }
    }


    sealed class States {
        data class Loaded(val items: List<UIPatient>) : States()

        object Loading : States()
    }

    sealed class Actions {
        object Start : Actions()
    }
}


