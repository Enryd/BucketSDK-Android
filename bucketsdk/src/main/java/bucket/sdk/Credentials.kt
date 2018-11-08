package bucket.sdk

import android.content.Context
import bucket.sdk.extensions.isNil

object Credentials {
    @JvmStatic val sharedPrefs = Bucket.appContext?.getSharedPreferences("SHAREDPREFS", Context.MODE_PRIVATE)

    @JvmStatic fun retailerCode(): String? {
        // The only thing hardcoded here is the /clientId.  This makes it so the hacker would need the actual device to read the clientId or clientSecret, which would mean we already have a security breach.
        return sharedPrefs?.getString("RETAILER_CODE", null)
    }
    @JvmStatic fun setRetailerCode(value: String?) {
        // The only thing hardcoded here is the /clientId.  This makes it so the hacker would need the actual device to read the clientId or clientSecret, which would mean we already have a security breach.
        val editor = sharedPrefs?.edit()
        if (value.isNil) { editor?.remove("RETAILER_CODE") }
        else {
            editor?.putString("RETAILER_CODE", value)
        }
        editor?.apply()
    }
    @JvmStatic fun terminalSecret(): String? {
        // The only thing hardcoded here is the /clientSecret.  This makes it so the hacker would need the actual device to read the clientId or clientSecret, which would mean we already have a security breach.
        return sharedPrefs?.getString("TERMINAL_SECRET", null)
    }
    @JvmStatic fun setTerminalSecret(value: String?) {
        // The only thing hardcoded here is the /clientSecret.  This makes it so the hacker would need the actual device to read the clientId or clientSecret, which would mean we already have a security breach.
        val editor = sharedPrefs?.edit()
        if (value.isNil) { editor?.remove("TERMINAL_SECRET") }
        else {
            editor?.putString("TERMINAL_SECRET", value)
        }
        editor?.apply()
    }
    @JvmStatic fun countryCode(): String? {
        return sharedPrefs?.getString("RETAILER_COUNTRY", null)
    }
    @JvmStatic fun setCountryCode(value : String?) {
        val editor = sharedPrefs?.edit()
        if (value.isNil) { editor?.remove("RETAILER_COUNTRY") }
        else {
            editor?.putString("RETAILER_COUNTRY", value)
        }
        editor?.apply()
    }
    @JvmStatic fun name(): String? {
        return sharedPrefs?.getString("RETAILER_NAME", null)
    }
    @JvmStatic fun setName(value : String?) {
        val editor = sharedPrefs?.edit()
        if (value.isNil) { editor?.remove("RETAILER_NAME") }
        else {
            editor?.putString("RETAILER_NAME", value)
        }
        editor?.apply()
    }
    @JvmStatic fun phone(): String? {
        return sharedPrefs?.getString("RETAILER_PHONE", null)
    }
    @JvmStatic fun setPhone(value : String?) {
        val editor = sharedPrefs?.edit()
        if (value.isNil) { editor?.remove("RETAILER_PHONE") }
        else {
            editor?.putString("RETAILER_PHONE", value)
        }
        editor?.apply()
    }
    @JvmStatic fun address(): String? {
        return sharedPrefs?.getString("RETAILER_ADDRESS", null)
    }
    @JvmStatic fun setAddress(value : String?) {
        val editor = sharedPrefs?.edit()
        if (value.isNil) { editor?.remove("RETAILER_ADDRESS") }
        else {
            editor?.putString("RETAILER_ADDRESS", value)
        }
        editor?.apply()
    }

}