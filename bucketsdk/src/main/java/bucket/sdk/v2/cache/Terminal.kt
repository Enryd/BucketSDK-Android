package bucket.sdk.v2.cache

import com.chibatching.kotpref.KotprefModel

internal object Terminal : KotprefModel() {
    @JvmStatic var usesNaturalChangeFunction by booleanPref(false)
    @JvmStatic var billDenoms by stringPref("100.00,50.00,20.00,10.00,5.00,2.00,1.00")
    @JvmStatic var isApproved by booleanPref(false)
}