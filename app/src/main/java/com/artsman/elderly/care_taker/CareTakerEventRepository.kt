package com.artsman.elderly.care_taker

import android.content.Context
import android.util.Log
import com.artsman.elderly.GenericData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

interface CareTakerEventRepository{
    fun fetchEvent():List<EventInfo>
    fun getEventList():List<Event>
}

data class EventInfo(
    val title:String,
    val goal:String,
    val steps:String
)

data class Event(val title:String, val goal:String, val steps:String, val photoUrl: String)

class AssetCareTakerEventRepository(val context: Context) : CareTakerEventRepository{
    override fun fetchEvent(): List<EventInfo> {
    val read=readFromAsset()
    val eventInfos=parseData(read)
    return eventInfos
    }

    override fun getEventList(): List<Event> {
        val data= parseData(readFromAsset())
        Log.d("NULLISSUE", "data: ${data}")
        val list = data.map {
            Event(it.title, it.goal,it.steps,"url")
        }
        return list;
    }

    private fun parseData(read: String): List<EventInfo> {
        val gson = Gson()
        val type= object: TypeToken<GenericData<List<EventInfo>>>(){}.type
        val dataObj = gson.fromJson<GenericData<List<EventInfo>>>(read, type)
        Log.d("NULLISSUE", "parseData:${dataObj}")
        return dataObj.data
    }

    private fun readFromAsset(): String {
        val data=context.assets.open("eventList.json").bufferedReader().use {
            it.readText()
        }
        Log.d("NULLISSUE", "readFromAsset: ${data}")
        return data
    }
}

