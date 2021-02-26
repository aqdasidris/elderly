package com.artsman.elderly.care_taker.repo

abstract class ISupportedEvent {}
data class StepInfo(val title: String, val goal: String, val steps: String, val photoUrl: String) :
    ISupportedEvent()

data class LocationInfo(val title: String, val lat: String, val long: String, val time: String) :
    ISupportedEvent()

data class ReminderInfo(val title: String, val time: Long, val repeat: Int) : ISupportedEvent()


open class UIEvent<out T : ISupportedEvent>(
    open val event_name: String,
    open val event_id: String,
    open val event_type: EventType,
    open val created_at: String,//todo long
    open val event_info: T
) {

    companion object {
        fun UIEvent<ISupportedEvent>.toDBEvent(): DBEvent {
            return DBEvent(
                event_name = this.event_name,
                event_id = this.event_id,
                event_type = this.event_type.name,
                created_at = this.created_at,
                event_info = this.event_info.toString()
            )
        }
    }
}

data class StepEvent(
    override val event_name: String,
    override val event_id: String,
    override val event_type: EventType,
    override val created_at: String,
    override val event_info: StepInfo
) : UIEvent<StepInfo>(
    event_name,
    event_id,
    event_type,
    created_at,
    event_info
) {

}

data class LocationEvent(
    override val event_name: String,
    override val event_id: String,
    override val event_type: EventType,
    override val created_at: String,
    override val event_info: LocationInfo
) : UIEvent<LocationInfo>(
    event_name,
    event_id,
    event_type,
    created_at,
    event_info
) {

}

data class ReminderEvent(
    override val event_name: String,
    override val event_id: String,
    override val event_type: EventType,
    override val created_at: String,
    override val event_info: ReminderInfo
) : UIEvent<ReminderInfo>(
    event_name,
    event_id,
    event_type,
    created_at,
    event_info
) {

}