package bucket.sdk.v2.cache

import com.chibatching.kotpref.KotprefModel

object Credentials : KotprefModel() {
    @JvmStatic internal var terminalSecret by nullableStringPref()
    @JvmStatic var retailerCode by nullableStringPref()
        internal set
    @JvmStatic var country by nullableStringPref()
        internal set
}
