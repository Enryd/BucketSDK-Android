package bucket.sdk.v2.json.events

import java.io.Serializable

interface GetEventsBody : Serializable

data class EventsDateStringsBody(
        val start: String,
        val end: String)
    : GetEventsBody

data class EventsEpochIntegersBody(
        val start: Int,
        val end: Int)
    : GetEventsBody

data class EventsIdBody(
        val id: Int)
    : GetEventsBody