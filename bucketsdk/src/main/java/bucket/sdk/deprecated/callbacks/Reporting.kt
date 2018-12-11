package bucket.sdk.deprecated.callbacks

import bucket.sdk.deprecated.models.Error
import bucket.sdk.v2.json.reporting.ReportTransaction

@Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
interface Reporting {
    fun success(bucketTotal: Double?, transactionsList: List<ReportTransaction>)
    fun didError(error: Error)
}