package com.artsman.elderly.care_taker.repo

import com.artsman.elderly.core.DatabaseProvider

class EventLocalProvider(val databaseProvider: DatabaseProvider){

    fun addEvents(vararg event: DBEvent){
        getEventDao()?.insertAll(*event)
    }

    fun getEvents(): List<DBEvent>{
        return getEventDao()?.getAllEvent() ?: listOf()
    }

    private fun getEventDao() = databaseProvider.getDatabase()?.getEventDao()
}