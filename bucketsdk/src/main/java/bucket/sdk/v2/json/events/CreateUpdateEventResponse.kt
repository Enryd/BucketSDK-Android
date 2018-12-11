package bucket.sdk.v2.json.events

import java.io.Serializable

data class CreateUpdateEventResponse(
        val id: Int,
        val result: String)
    : Serializable
