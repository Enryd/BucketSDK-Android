package bucket.sdk.cache

import com.chibatching.kotpref.KotprefModel

object Preferences : KotprefModel() {
    var billDenoms by stringPref("100.00,50.00,20.00,10.00,5.00,2.00,1.00")
}