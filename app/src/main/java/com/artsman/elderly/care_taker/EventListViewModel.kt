package com.artsman.elderly.care_taker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.artsman.elderly.care_taker.repo.CareTakerEventRepository
import com.artsman.elderly.care_taker.repo.EventInfo
import com.artsman.elderly.care_taker.repo.ISupportedEvent
import com.artsman.elderly.care_taker.repo.UIEvent

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EventListViewModel(val repo: CareTakerEventRepository) : ViewModel() {

    private val mCurrentState = MediatorLiveData<States>()

    fun subscribe(): LiveData<States> {
        return mCurrentState
    }

    fun setAction(action: Actions) {
        mCurrentState.postValue(States.Loading)
        GlobalScope.launch(Dispatchers.IO) {
            repo.getEventFromRemote()
        }
        mCurrentState.addSource(repo.getEventFromLocal()) { items ->
            mCurrentState.postValue(States.Loaded(items = items))
        }
    }


    sealed class States {
        data class Loaded(val items: List<UIEvent<ISupportedEvent>>) : States()

        object Loading : States()
    }

    sealed class Actions {
        object Start : Actions()
    }
}



