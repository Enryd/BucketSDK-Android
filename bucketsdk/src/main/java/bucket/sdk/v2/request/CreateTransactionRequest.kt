package bucket.sdk.v2.request

data class CreateTransactionRequest(val amount: Double) {
    var totalTransactionAmount: Double? = null
    var locationId: String? = null
    var clientTransactionId: String? = null
    var sample: Boolean? = false
    var employeeCode: String? = null
    var eventId: Int? = null
}