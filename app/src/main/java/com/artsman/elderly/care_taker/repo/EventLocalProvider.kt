package com.artsman.elderly.care_taker.repo

import androidx.lifecycle.LiveData
import com.artsman.elderly.core.DatabaseProvider

class EventLocalProvider(val databaseProvider: DatabaseProvider){

    fun addEvents(vararg event: DBEvent){
        getEventDao()?.insertAll(*event)
    }

    fun getEvents(): LiveData<List<DBEvent>>? {
        return getEventDao()?.getAllEvent()
    }

    private fun getEventDao() = databaseProvider.getDatabase()?.getEventDao()
}