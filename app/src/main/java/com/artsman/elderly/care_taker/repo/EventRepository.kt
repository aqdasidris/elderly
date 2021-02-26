package com.artsman.elderly.care_taker.repo

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.artsman.elderly.GenericData
import com.artsman.elderly.care_taker.repo.AbstractEvent.Companion.toDBEvent
import com.artsman.elderly.care_taker.repo.DBEvent.Companion.toUIEvent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

interface CareTakerEventRepository{
    //fun fetchEvent():List<EventInfo>
    fun getEventList():List<StepInfo>
    fun getEventFromLocal(): LiveData<List<UIEvent<ISupportedEvent>>>
    suspend fun fetchEventRemote():List<EventInfo>?
    suspend fun getEventFromRemote()
}

enum class EventType{
    steps, location, reminder
}


/*data class StepTypeEvent(
    override val  event_name: String,
    override val  event_id: String,
    override val  event_type: EventType,
    override val  created_at: String,
    override val event_info: StepInfo
): AbstractEvent<StepInfo>(event_name, event_id, event_type, created_at, event_info)*/



@Deprecated("Will be replace by event generic")
data class EventInfo(
    val title:String,
    val goal:String,
    val steps:String,
)



class EventRepository(val context: Context, val remote: EventRemoteProvider, val local: EventLocalProvider ) :
    CareTakerEventRepository {


    override fun getEventList(): List<StepInfo> {
        val data= parseData(readFromAsset())
        Log.d("NULLISSUE", "data: ${data}")
        val list = data.map {
            StepInfo(it.title, it.goal,it.steps,"url")
        }
        return list;
    }

    override fun getEventFromLocal(): LiveData<List<UIEvent<ISupportedEvent>>> {
       val fromDb: LiveData<List<DBEvent>>? = local.databaseProvider.getDatabase()?.getEventDao()?.getAllEvent()
        val bridge=Transformations.map(fromDb as LiveData<List<DBEvent>>){
            return@map it.map {
                it.toUIEvent()
            }
        }
        return bridge
    }


    private fun parseData(read: String): List<EventInfo> {
        val gson = Gson()
        val type= object: TypeToken<GenericData<List<EventInfo>>>(){}.type
        val dataObj = gson.fromJson<GenericData<List<EventInfo>>>(read, type)
        Log.d("NULLISSUE", "parseData:${dataObj}")
        return dataObj.data
    }

    override suspend fun fetchEventRemote():List<EventInfo>?{
        val response = remote.getEvents()?.execute()
        if (response?.isSuccessful == true) {
            Log.d("API", response.body().toString())
        }else {
            Log.d("API", "Failed: ${response?.errorBody()}")
        }
        return response?.body()?.data
    }

    override suspend fun getEventFromRemote() {
        val response = remote.getEventsV2()?.execute()
        if (response?.isSuccessful == true) {
//            Log.d("APIV2", Gson().toJson(response.body()))
            response.body()?.data?.forEach {
                val toDBEvent = it.toDBEvent()
                Log.d("APIV2", toDBEvent.toString())
                local.addEvents(toDBEvent)
            }
        }else {
            Log.d("APIV2", "Failed: ${response?.errorBody()}")
        }
    }

    private fun readFromAsset(): String {
        val data=context.assets.open("eventList.json").bufferedReader().use {
            it.readText()
        }
        Log.d("NULLISSUE", "readFromAsset: ${data}")
        return data
    }
}

