package com.artsman.elderly.events
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class AddEventViewModel{
    private var mEventState= MutableLiveData<AddEvent>()

    fun setEventState(): LiveData<AddEvent> {
        mEventState.postValue(AddEvent.addEventState)
        return mEventState
    }

    fun setEventAction(eventAction: EventAction) {
        if(eventAction==EventAction.add_event_action){
            mEventState.postValue(AddEvent.success_state)
        }
    }
}

enum class EventAction {
    add_event_action

}

enum class AddEvent {
    addEventState,
    success_state,

}
