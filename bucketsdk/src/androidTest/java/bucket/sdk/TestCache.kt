package bucket.sdk

import com.chibatching.kotpref.KotprefModel

internal object TestCache : KotprefModel() {
    @JvmStatic var customerCode by stringPref()
    @JvmStatic var eventId by intPref()
}