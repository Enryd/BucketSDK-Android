package bucket.sdk.callbacks

import bucket.sdk.models.Error
import bucket.sdk.models.responses.ReportTransaction

interface Reporting {
    fun success(bucketTotal: Double?, transactionsList: List<ReportTransaction>)
    fun didError(error: Error)
}