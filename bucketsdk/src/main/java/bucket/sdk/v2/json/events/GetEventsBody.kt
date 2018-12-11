package bucket.sdk.v2.json.events

import java.io.Serializable

data class EventsDateStringsBody(
        val start: String,
        val end: String)
    : Serializable

data class EventsEpochIntegersBody(
        val start: Int,
        val end: Int)
    : Serializable

data class EventsIdBody(
        val id: Int)
    : Serializable
