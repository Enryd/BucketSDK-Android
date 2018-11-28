package bucket.sdk.v2.cache

import com.chibatching.kotpref.KotprefModel

object Terminal : KotprefModel() {
    @JvmStatic internal var usesNaturalChangeFunction by booleanPref(false)
    @JvmStatic internal var billDenoms by stringPref("100.00,50.00,20.00,10.00,5.00,2.00,1.00")
    @JvmStatic var isApproved by booleanPref(false)
        internal set
}