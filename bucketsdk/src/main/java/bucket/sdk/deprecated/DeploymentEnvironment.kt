package bucket.sdk.deprecated

import android.net.Uri

@Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
enum class DeploymentEnvironment {

    // Cases: (PRODUCTION & DEVELOPMENT for now)
    Production, Development, Staging;

    // Case URL Endpoint:
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    private fun bucketBaseUri(): Uri.Builder {
        val builder = Uri.Builder()
        builder.scheme("https")
        when (this) {
            Production -> builder.authority("prod.bucketthechange.com")
            Development -> builder.authority("dev.bucketthechange.com")
            Staging -> builder.authority("staging.bucketthechange.com")
        }
        builder.appendPath("api")
        builder.appendPath("v1")
        return builder
    }

    // Case URL Endpoint:
//    private fun bucketBaseUri() = Uri.Builder().apply {
//        scheme(URI_SCHEME)
//        authority(when(this@DeploymentEnvironment) {
//            PRODUCTION -> URI_AUTHORITY_PRODUCTION
//            DEVELOPMENT -> URI_AUTHORITY_DEVELOPMENT
//            STAGING -> URI_AUTHORITY_STAGING
//        })
//        appendPath("api")
//        appendPath(API_VERSION)
//    }

    // PRE-BUILT ENDPOINT PATHS:
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    var transaction : Uri.Builder
        get() = bucketBaseUri().appendPath("transaction")
        private set(value) {}

    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    var registerTerminal : Uri.Builder
        get() = bucketBaseUri().appendPath("registerterminal")
        private set(value) {}

    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    var billDenoms : Uri.Builder
        get() = bucketBaseUri().appendPath("billDenoms")
        private set(value) {}

    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    var report : Uri.Builder
        get() = bucketBaseUri().appendPath("report")
        private set(value) {}

}