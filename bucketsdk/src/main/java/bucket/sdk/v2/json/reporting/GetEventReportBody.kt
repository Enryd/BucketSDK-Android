package bucket.sdk.v2.json.reporting

import java.io.Serializable

/**
 * @param start This date is formatted as 'yyyy-MM-dd HH:mm:ssZZZ'
 * @param end This date is formatted as 'yyyy-MM-dd HH:mm:ssZZZ'
 */
data class EventReportDateStringsBody(
        val start: String,
        val end: String)
    : Serializable

/**
 * @param start This is the starting epoch integer in SECONDS that is UTC based.
 * @param end This is the ending epoch integer in SECONDS that is UTC based.
 */
data class EventReportEpochIntegersBody(
        val start: Int,
        val end: Int)
    : Serializable

/**
 * @param id Event id to get reports from
 */
data class EventReportWithId(
        val id: Int)
    : Serializable


