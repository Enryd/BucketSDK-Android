package bucket.sdk.v2.json.reporting

import java.io.Serializable

interface GetReportBody : Serializable

/**
 * @param start This date is formatted as 'yyyy-MM-dd HH:mm:ssZZZ'
 * @param end This date is formatted as 'yyyy-MM-dd HH:mm:ssZZZ'
 * @param reportTerminalCode This is the serial number of the terminal for the report.
 * | This could be different than the header value, as the header value is always the
 * | terminal for the request
 * @param employeeCode This is the employeeCode to change the report results.
 * @param employeeId This would be to filter the results based on a report that is
 * | given for all employees. Since you wouldn't know their employeeCode,
 * | you can filter the report by their id.
 */
data class ReportDateStringsBody(
        val start: String,
        val end: String,
        val reportTerminalCode: String?,
        val employeeCode: String?,
        val employeeId: Int?)
    : GetReportBody

/**
 * @param start This is the starting epoch integer in SECONDS that is UTC based.
 * @param end This is the ending epoch integer in SECONDS that is UTC based.
 * @param reportTerminalCode This is the serial number of the terminal for the report.
 * | This could be different than the header value, as the header value is always the
 * | terminal for the request
 * @param employeeCode This is the employeeCode to change the report results.
 * @param employeeId This would be to filter the results based on a report that is
 * | given for all employees. Since you wouldn't know their employeeCode,
 * | you can filter the report by their id.
 */
data class ReportEpochIntegersBody(
        val start: Int,
        val end: Int,
        val reportTerminalCode: String?,
        val employeeCode: String?,
        val employeeId: Int?)
    : GetReportBody

/**
 * @param day This is formatted as 'yyyy-MM-dd'. This covers starting from 12AM that day to 11:59:59PM that day.
 * @param reportTerminalCode This is the serial number of the terminal for the report.
 * | This could be different than the header value, as the header value is always the
 * | terminal for the request
 * @param employeeCode This is the employeeCode to change the report results.
 * @param employeeId This would be to filter the results based on a report that is
 * | given for all employees. Since you wouldn't know their employeeCode,
 * | you can filter the report by their id.
 */
data class ReportDayStringBody(
        val day: String,
        val reportTerminalCode: String?,
        val employeeCode: String?,
        val employeeId: Int?)
    : GetReportBody


