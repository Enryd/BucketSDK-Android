package bucket.sdk.models.responses

import java.io.Serializable

data class ReportingResponse(
        val bucketTotal: Double,
        val apiKey: String,
        val retailerName: String,
        val retailerPhone: String,
        val address: Address,
        val errorCode: String?,
        val message: String?)
    : Serializable

data class ReportTransaction(
        val bucketTransactionId: Int?,
        val created: String?,
        val amount: Double?,
        val totalTransactionAmount: Double?,
        val customerCode: String?,
        val disputed: String?,
        val disputedBy: String?,
        val locationId: String?,
        val clientTransactionId: String?,
        val terminalId: String?)
    : Serializable
