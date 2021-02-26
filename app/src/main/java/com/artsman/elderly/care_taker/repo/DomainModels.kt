package com.artsman.elderly.care_taker.repo

import org.json.JSONObject

open class AbstractEvent<T: Map<String, Any>>(
    open val event_name: String,
    open val event_id: String,
    open val event_type: EventType,
    open val created_at: String,//todo long
    open val event_info: T){

    companion object{
        fun AbstractEvent<Map<String, Any>>.toDBEvent(): DBEvent{
            return DBEvent(event_name = this.event_name, event_id = this.event_id, event_type = this.event_type.name, created_at = this.created_at, event_info = JSONObject(this.event_info).toString())
        }
    }
}