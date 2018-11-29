package bucket.sdk.v2.json.events

data class EventsDateStringsBody(
        val start: String,
        val end: String)

data class EventsEpochIntegersBody(
        val start: Int,
        val end: Int)

data class EventsIdBody(
        val id: Int)
