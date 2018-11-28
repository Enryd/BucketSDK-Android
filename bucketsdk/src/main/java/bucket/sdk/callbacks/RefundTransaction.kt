package bucket.sdk.callbacks

import bucket.sdk.models.Error
import bucket.sdk.models.responses.ReportTransaction

interface RefundTransaction {
    fun success(message: String)
    fun didError(error: Error)
}