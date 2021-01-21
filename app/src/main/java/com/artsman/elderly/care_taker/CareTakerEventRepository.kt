package com.artsman.elderly.care_taker

import android.content.Context
import android.util.Log
import com.artsman.elderly.GenericData
import com.artsman.elderly.care_taker.api.EventListApi
import com.artsman.elderly.patient_info.PatientInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

interface CareTakerEventRepository{
    //fun fetchEvent():List<EventInfo>
    fun getEventList():List<Event>
    suspend fun fetchEventRemote():List<EventInfo>?
}

data class EventInfo(
    val title:String,
    val goal:String,
    val steps:String,
)

data class Event(val title:String, val goal:String, val steps:String, val photoUrl: String)

class AssetCareTakerEventRepository(val context: Context, val eventListApi: EventListApi) : CareTakerEventRepository{
//    override fun fetchEvent(): List<EventInfo> {
//    val read=readFromAsset()
//    val eventInfos=parseData(read)
//    return eventInfos
//    }


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

    override suspend fun fetchEventRemote():List<EventInfo>?{
        val response = eventListApi.getEvents()?.execute()
        if (response?.isSuccessful == true) {
            Log.d("API", response.body().toString())
        }else {
            Log.d("API", "Failed: ${response?.errorBody()}")
        }
        return response?.body()?.data
    }

    private fun readFromAsset(): String {
        val data=context.assets.open("eventList.json").bufferedReader().use {
            it.readText()
        }
        Log.d("NULLISSUE", "readFromAsset: ${data}")
        return data
    }
}

