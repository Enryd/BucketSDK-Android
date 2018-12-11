package bucket.sdk.deprecated

import android.content.Context
import bucket.sdk.deprecated.extensions.isNil

@Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
object Credentials {
    @JvmStatic
    internal val sharedPrefs = Bucket.appContext?.getSharedPreferences("SHAREDPREFS", Context.MODE_PRIVATE)

    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    @JvmStatic fun retailerCode(): String? {
        // The only thing hardcoded here is the /clientId.  This makes it so the hacker would need the actual device to read the clientId or clientSecret, which would mean we already have a security breach.
        return sharedPrefs?.getString("RETAILER_CODE", null)
    }
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    @JvmStatic fun setRetailerCode(value: String?) {
        // The only thing hardcoded here is the /clientId.  This makes it so the hacker would need the actual device to read the clientId or clientSecret, which would mean we already have a security breach.
        val editor = sharedPrefs?.edit()
        if (value.isNil) { editor?.remove("RETAILER_CODE") }
        else {
            editor?.putString("RETAILER_CODE", value)
        }
        editor?.apply()
    }
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    @JvmStatic fun terminalSecret(): String? {
        // The only thing hardcoded here is the /clientSecret.  This makes it so the hacker would need the actual device to read the clientId or clientSecret, which would mean we already have a security breach.
        return sharedPrefs?.getString("TERMINAL_SECRET", null)
    }
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    @JvmStatic fun setTerminalSecret(value: String?) {
        // The only thing hardcoded here is the /clientSecret.  This makes it so the hacker would need the actual device to read the clientId or clientSecret, which would mean we already have a security breach.
        val editor = sharedPrefs?.edit()
        if (value.isNil) { editor?.remove("TERMINAL_SECRET") }
        else {
            editor?.putString("TERMINAL_SECRET", value)
        }
        editor?.apply()
    }
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    @JvmStatic fun countryCode(): String? {
        return sharedPrefs?.getString("RETAILER_COUNTRY", null)
    }
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    @JvmStatic fun setCountryCode(value : String?) {
        val editor = sharedPrefs?.edit()
        if (value.isNil) { editor?.remove("RETAILER_COUNTRY") }
        else {
            editor?.putString("RETAILER_COUNTRY", value)
        }
        editor?.apply()
    }
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    @JvmStatic fun name(): String? {
        return sharedPrefs?.getString("RETAILER_NAME", null)
    }
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    @JvmStatic fun setName(value : String?) {
        val editor = sharedPrefs?.edit()
        if (value.isNil) { editor?.remove("RETAILER_NAME") }
        else {
            editor?.putString("RETAILER_NAME", value)
        }
        editor?.apply()
    }
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    @JvmStatic fun phone(): String? {
        return sharedPrefs?.getString("RETAILER_PHONE", null)
    }
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    @JvmStatic fun setPhone(value : String?) {
        val editor = sharedPrefs?.edit()
        if (value.isNil) { editor?.remove("RETAILER_PHONE") }
        else {
            editor?.putString("RETAILER_PHONE", value)
        }
        editor?.apply()
    }
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    @JvmStatic fun address(): String? {
        return sharedPrefs?.getString("RETAILER_ADDRESS", null)
    }
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    @JvmStatic fun setAddress(value : String?) {
        val editor = sharedPrefs?.edit()
        if (value.isNil) { editor?.remove("RETAILER_ADDRESS") }
        else {
            editor?.putString("RETAILER_ADDRESS", value)
        }
        editor?.apply()
    }
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    @JvmStatic fun address1(): String? {
        return sharedPrefs?.getString("RETAILER_ADDRESS1", null)
    }
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    @JvmStatic fun setAddress1(value : String?) {
        val editor = sharedPrefs?.edit()
        if (value.isNil) { editor?.remove("RETAILER_ADDRESS1") }
        else {
            editor?.putString("RETAILER_ADDRESS1", value)
        }
        editor?.apply()
    }
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    @JvmStatic fun address2(): String? {
        return sharedPrefs?.getString("RETAILER_ADDRESS2", null)
    }
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    @JvmStatic fun setAddress2(value : String?) {
        val editor = sharedPrefs?.edit()
        if (value.isNil) { editor?.remove("RETAILER_ADDRESS2") }
        else {
            editor?.putString("RETAILER_ADDRESS2", value)
        }
        editor?.apply()
    }
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    @JvmStatic fun address3(): String? {
        return sharedPrefs?.getString("RETAILER_ADDRESS3", null)
    }
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    @JvmStatic fun setAddress3(value : String?) {
        val editor = sharedPrefs?.edit()
        if (value.isNil) { editor?.remove("RETAILER_ADDRESS3") }
        else {
            editor?.putString("RETAILER_ADDRESS3", value)
        }
        editor?.apply()
    }
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    @JvmStatic fun city(): String? {
        return sharedPrefs?.getString("RETAILER_CITY", null)
    }
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    @JvmStatic fun setCity(value : String?) {
        val editor = sharedPrefs?.edit()
        if (value.isNil) { editor?.remove("RETAILER_CITY") }
        else {
            editor?.putString("RETAILER_CITY", value)
        }
        editor?.apply()
    }
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    @JvmStatic fun state(): String? {
        return sharedPrefs?.getString("RETAILER_STATE", null)
    }
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    @JvmStatic fun setState(value : String?) {
        val editor = sharedPrefs?.edit()
        if (value.isNil) { editor?.remove("RETAILER_STATE") }
        else {
            editor?.putString("RETAILER_STATE", value)
        }
        editor?.apply()
    }
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    @JvmStatic fun postalCode(): String? {
        return sharedPrefs?.getString("RETAILER_POSTAL_CODE", null)
    }
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    @JvmStatic fun setPostalCode(value : String?) {
        val editor = sharedPrefs?.edit()
        if (value.isNil) { editor?.remove("RETAILER_POSTAL_CODE") }
        else {
            editor?.putString("RETAILER_POSTAL_CODE", value)
        }
        editor?.apply()
    }

}