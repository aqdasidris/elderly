package com.artsman.elderly.care_taker

import android.util.Log
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
        when(action){
            is Actions.Delete -> {
                GlobalScope.launch(Dispatchers.IO) {
                    repo.remove(action.uiEvent)

                }
            }
            Actions.Start -> {
                mCurrentState.postValue(States.Loading)
                GlobalScope.launch(Dispatchers.IO) {
                    repo.getEventFromRemote()
                }
                mCurrentState.addSource(repo.getEventFromLocal()) { items ->
                    Log.d("DELETE", "setAction: On Change- Size: ${items}")
                    mCurrentState.postValue(States.Loaded(items = items))
                }
            }
        }

    }


    sealed class States {
        data class Loaded(val items: List<UIEvent<ISupportedEvent>>) : States()

        object Loading : States()
    }

    sealed class Actions {
        object Start : Actions()
        data class Delete(val uiEvent: UIEvent<ISupportedEvent>): Actions()
    }
}



