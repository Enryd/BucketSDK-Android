package bucket.sdk.v2.json.events

import java.io.Serializable

interface CreateUpdateEventBody : Serializable

interface CreateEventBody : CreateUpdateEventBody
interface UpdateEventBody : CreateUpdateEventBody

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
    : CreateEventBody

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
    : CreateEventBody

/**
 * @param eventName This is the name of the event.
 * @param eventMessage This is the message of the event.
 * @param start The date format is yyyy-MM-dd HH:mm:ssZZZ
 * @param end The date format is yyyy-MM-dd HH:mm:ssZZZ
 */
data class UpdateEventDateStringsBody(
        val id: Int,
        val eventName: String?,
        val eventMessage: String?,
        val start: String?,
        val end: String?)
    : UpdateEventBody

/**
 * @param eventName This is the name of the event.
 * @param eventMessage This is the message of the event.
 * @param start This is the starting epoch integer in SECONDS that is UTC based.
 * @param end This is the ending epoch integer in SECONDS that is UTC based.
 */
data class UpdateEventEpochIntegersBody(
        val id: Int,
        val eventName: String?,
        val eventMessage: String?,
        val start: Int?,
        val end: Int?)
    : UpdateEventBody