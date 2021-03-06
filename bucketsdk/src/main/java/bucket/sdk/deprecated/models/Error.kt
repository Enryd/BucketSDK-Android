package bucket.sdk.deprecated.models

@Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
class Error(var message: String?, var errorCode : String?, var code : Int?) {
    companion object {
        @JvmStatic val unauthorized : Error = Error("Check your retailer id & retailer secret", "Unauthorized", 401)
        @JvmStatic val invalidCountryCode : Error = Error("No such country code found", "InvalidCountryCode", 409)
    }
}