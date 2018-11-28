package bucket.sdk.v2.json

import java.io.Serializable

data class ErrorResponse(
        val error: String?,
        val errorCode: String?,
        val message: String?)
    : Serializable