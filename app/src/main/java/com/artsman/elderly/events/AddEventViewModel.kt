package com.artsman.elderly.events
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.artsman.elderly.eventss.EventDetails


class AddEventViewModel(val eventRepo: EventDataRepository){
    private var mEventState= MutableLiveData<AddEvent>()

    fun setEventState(): LiveData<AddEvent> {
        mEventState.postValue(AddEvent.addEventState)
        return mEventState
    }

    fun setEventAction(eventAction: EventAction) {
        if(eventAction==EventAction.add_event_action){
            mEventState.postValue(AddEvent.success_state)
            eventRepo.saveEventData(saveEventData())
        }
    }

    val registrationMap= mutableMapOf<String, String>()
    fun putValue(key: String, value: String) {
        registrationMap.put(key, value)
    }



    private fun saveEventData(): EventDetails {
        return EventDetails(event_name = registrationMap["event_name"] ?: "",
        start_time = registrationMap["start_time"] ?: "",
        select_days= registrationMap["select_days"] ?: "",
        set_frequency = registrationMap["set_frequency"] ?: "",
        end_time = registrationMap["end_time"] ?: "")

    }

    companion object{
        const val EVENT_NAME="event_name"
        const val START_TIME="start_time"
        const val SELECT_DAYS="select_days"
        const val SET_FREQUENCY="set_frequency"
        const val END_TIME="end_time"
    }
}

enum class EventAction {
    add_event_action

}

enum class AddEvent {
    addEventState,
    success_state,

}
