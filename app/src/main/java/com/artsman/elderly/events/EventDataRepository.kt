package com.artsman.elderly.events

import android.util.Log
import com.artsman.elderly.data.IPreferenceHelper
import com.artsman.elderly.eventss.EventDetails

interface EventDataRepository {
    fun saveEventData(event : EventDetails)
}

class EventRepository(prefHelper: IPreferenceHelper) :EventDataRepository{
    override fun saveEventData(event : EventDetails) {
        Log.d("Event", event.toString() )
    }
}

