package com.artsman.elderly.care_taker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.artsman.elderly.GenericData

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EventListViewModel(val repo:CareTakerEventRepository): ViewModel() {

    private val mCurrentState = MutableLiveData<States>()
    fun subscribe(): LiveData<States> {
        return mCurrentState
    }

    fun setAction(action: Actions) {
        mCurrentState.postValue(States.Loading)
        GlobalScope.launch(Dispatchers.IO) {
            val data=repo.fetchEventRemote()
            data?.let {
                GlobalScope.launch(Dispatchers.Main) {
                    mCurrentState.postValue(States.Loaded(it))
                }

            }
        }
    }


    sealed class States {
        data class Loaded(val items:List<EventInfo>) : States()

        object Loading : States()
    }

    sealed class Actions {
        object Start : Actions()
    }

}


