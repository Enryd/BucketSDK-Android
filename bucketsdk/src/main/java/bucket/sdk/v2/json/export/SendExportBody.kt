package bucket.sdk.v2.json.export

import java.io.Serializable

data class SendExportBody(
        val email: String,
        val eventId: Int? = null)
    : Serializable
