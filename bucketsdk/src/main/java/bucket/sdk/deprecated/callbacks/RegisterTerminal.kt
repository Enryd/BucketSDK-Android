package bucket.sdk.deprecated.callbacks

import bucket.sdk.deprecated.models.Error

@Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
interface RegisterTerminal {
    fun success(isApproved: Boolean)
    fun didError(error: Error)
}