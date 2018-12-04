package bucket.sdk.v2.cache

import com.chibatching.kotpref.KotprefModel

object TestCache : KotprefModel() {
    @JvmStatic internal var customerCode by stringPref()
}