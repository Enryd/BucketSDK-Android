package bucket.sdk.v2.json.transaction

data class TransactionBody(
        val amount: Double,
        var totalTransactionAmount: Double? = null,
        var locationId: String? = null,
        var clientTransactionId: String? = null,
        var sample: Boolean? = false)
