package com.artsman.elderly.patient_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.artsman.elderly.care_taker.CareTakerEventRepository
import com.artsman.elderly.care_taker.EventInfo
import com.artsman.elderly.patient_info.PAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class PatientListViewModel(val repo: PatientListRepository): ViewModel() {

    private val mCurrentState = MutableLiveData<States>()
    fun subscribe(): LiveData<States> {
        return mCurrentState
    }

    fun setAction(action: Actions) {
        mCurrentState.postValue(States.Loading)
        GlobalScope.launch(Dispatchers.IO) {
            val data=repo.fetchPatientListRemote()
            data?.let {
                GlobalScope.launch(Dispatchers.Main) {
                    mCurrentState.postValue(States.Loaded(it))
                }

            }
        }
    }


    sealed class States {
        data class Loaded(val items:List<PatientListItem>) : States()

        object Loading : States()
    }

    sealed class Actions {
        object Start : Actions()
    }

}
