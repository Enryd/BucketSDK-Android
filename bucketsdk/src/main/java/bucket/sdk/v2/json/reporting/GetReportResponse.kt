package bucket.sdk.v2.json.reporting

import java.io.Serializable

data class GetReportResponse(
        val totalTransactionCount: Int?,
        val bucketTotal: Double?,
        val bucketSales: Double?,
        val refundedBucketTotal: Double?,
        val refundedBucketSales: Double?,
        val transactions: List<ReportTransaction>)
    : Serializable

data class ReportTransaction(
        val bucketTransactionId: Int?,
        val created: String?,
        val amount: Double?,
        val totalTransactionAmount: Double?,
        val customerCode: String?,
        val disputed: String?,
        val disputedBy: String?,
        val refunded: String?,
        val refundedBy: String?,
        val locationId: String?,
        val clientTransactionId: String?,
        val terminalCode: String?,
        val employeeId: Int?)
    : Serializable