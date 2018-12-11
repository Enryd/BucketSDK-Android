package bucket.sdk.v2.json.reporting

import java.io.Serializable

data class GetEventReportResponse(
        val events: List<EventReport>?)
    : Serializable

data class EventReport(
        val id: Int?,
        val eventName: String?,
        val eventMessage: String?,
        val startDate: String?,
        val endDate: String?,
        val bucketTotal: Double?,
        val bucketSales: Double?,
        val refundedBucketTotal: Double?,
        val refundedBucketSales: Double?,
        val transactions: List<EventTransaction>?)
    : Serializable

data class EventTransaction(
        val bucketTransactionId: Int?,
        val created: String?,
        val amount: Double?,
        val totalTransactionAmount: Double?,
        val customerCode: String?,
        val disputed: String?,
        val disputedBy: String?,
        val refunded: String?,
        val refundedBy: String?,
        val redeemed: String?,
        val redeemedBy: String?,
        val locationId: String?,
        val clientTransactionId: String?,
        val terminalCode: String?,
        val employeeId: Int?,
        val eventName: String?,
        val eventId: Int?)
    : Serializable