package bucket.sdk.v2.json.events

import java.io.Serializable

data class GetEventsResponse(
        val events: List<Event>?)
    : Serializable

data class Event(
        val id: Int?,
        val eventName: String?,
        val eventMessage: String?,
        val startDate: String?,
        val endDate: String?)
    : Serializable