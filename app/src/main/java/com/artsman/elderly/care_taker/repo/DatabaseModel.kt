package com.artsman.elderly.care_taker.repo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson

@Entity(tableName = "events_table")
data class DBEvent(
    @PrimaryKey(autoGenerate = true)val id:Int=0,
    val event_name: String,
    val event_id: String,
    val event_type: String,
    val created_at: String,
    val event_info: String
) {

    companion object {
        fun DBEvent.toUIEvent(): UIEvent<ISupportedEvent> {
            return when(this.event_type){
                "steps"->{
                   StepEvent(this.event_name, this.event_id, EventType.valueOf(this.event_type), this.created_at, Gson().fromJson(this.event_info, StepInfo::class.java))
                }
                "location"->{
                    LocationEvent(this.event_name, this.event_id, EventType.valueOf(this.event_type), this.created_at, Gson().fromJson(this.event_info, LocationInfo::class.java))
                }
               else ->{
                   ReminderEvent(this.event_name, this.event_id, EventType.valueOf(this.event_type), this.created_at, Gson().fromJson(this.event_info, ReminderInfo::class.java))
               }

            }
        }
    }
}