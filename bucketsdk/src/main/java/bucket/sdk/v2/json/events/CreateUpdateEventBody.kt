package bucket.sdk.v2.json.events

import java.io.Serializable

/**
 * @param eventName This is the name of the event.
 * @param eventMessage This is the message of the event.
 * @param start The date format is yyyy-MM-dd HH:mm:ssZZZ
 * @param end The date format is yyyy-MM-dd HH:mm:ssZZZ
 */
data class CreateEventDateStringsBody(
        val eventName: String,
        val eventMessage: String,
        val start: String,
        val end: String)
    : Serializable

/**
 * @param eventName This is the name of the event.
 * @param eventMessage This is the message of the event.
 * @param start This is the starting epoch integer in SECONDS that is UTC based.
 * @param end This is the ending epoch integer in SECONDS that is UTC based.
 */
data class CreateEventEpochIntegersBody(
        val eventName: String,
        val eventMessage: String,
        val start: Int,
        val end: Int)
    : Serializable

data class UpdateEventDateStringsBody(
        val id: Int,
        val eventName: String? = null,
        val eventMessage: String? = null,
        val start: String? = null,
        val end: String? = null)
    : Serializable

data class UpdateEventEpochIntegersBody(
        val id: Int,
        val eventName: String? = null,
        val eventMessage: String? = null,
        val start: Int? = null,
        val end: Int? = null)
    : Serializable