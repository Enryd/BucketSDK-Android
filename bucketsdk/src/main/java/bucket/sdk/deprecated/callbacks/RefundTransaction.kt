package bucket.sdk.deprecated.callbacks

import bucket.sdk.deprecated.models.Error

@Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
interface RefundTransaction {
    fun success(message: String)
    fun didError(error: Error)
}