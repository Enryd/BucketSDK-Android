package bucket.sdk.v2.cache

import com.chibatching.kotpref.KotprefModel

object RetailerInfo : KotprefModel() {
    @JvmStatic var retailerName by nullableStringPref()
        internal set
    @JvmStatic var retailerPhone by nullableStringPref()
        internal set
    @JvmStatic var requireEmployeeCode by booleanPref(false)
        internal set
    @JvmStatic var address1 by nullableStringPref()
        internal set
    @JvmStatic var address2 by nullableStringPref()
        internal set
    @JvmStatic var address3 by nullableStringPref()
        internal set
    @JvmStatic var postalCode by nullableStringPref()
        internal set
    @JvmStatic var city by nullableStringPref()
        internal set
    @JvmStatic var state by nullableStringPref()
        internal set
}
