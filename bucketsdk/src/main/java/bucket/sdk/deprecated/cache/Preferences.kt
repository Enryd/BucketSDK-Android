package bucket.sdk.deprecated.cache

import com.chibatching.kotpref.KotprefModel

@Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
object Preferences : KotprefModel() {
    var billDenoms by stringPref("100.00,50.00,20.00,10.00,5.00,2.00,1.00")
}