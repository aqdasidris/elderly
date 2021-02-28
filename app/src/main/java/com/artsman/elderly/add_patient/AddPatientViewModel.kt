package com.artsman.elderly.add_patient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AddPatientViewModel {
   private var mCurrentStates=MutableLiveData<States>()

    fun getState():LiveData<States>{
        mCurrentStates.postValue(States.LoadedState)
        return mCurrentStates
    }

    fun setAction(action:Actions){
        if(action==Actions.Start){
            mCurrentStates.postValue(States.LoadedState)
        }
    }




   sealed class States{
       object Loading:States()
       object LoadedState:States()
   }

    sealed class Actions{
        object Start:Actions()
    }
}