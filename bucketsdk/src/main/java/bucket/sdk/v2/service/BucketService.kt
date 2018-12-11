package bucket.sdk.v2.service

import android.net.Uri
import bucket.sdk.v2.Bucket
import bucket.sdk.v2.DeploymentEnvironment
import bucket.sdk.v2.util.WebServiceHelper

internal object BucketService {

    private const val URI_SCHEME = "https"

    private const val URI_AUTHORITY_PRODUCTION = "prod.bucketthechange.com"
    private const val URI_AUTHORITY_DEVELOPMENT = "dev.bucketthechange.com"
    private const val URI_AUTHORITY_STAGING = "staging.bucketthechange.com"

    private const val API_VERSION = "v1"

    @JvmStatic private val env get() = Bucket.environment

    private val baseUri get() = Uri.Builder().apply {
        scheme(URI_SCHEME)
        authority(when(env) {
            DeploymentEnvironment.PRODUCTION -> URI_AUTHORITY_PRODUCTION
            DeploymentEnvironment.DEVELOPMENT -> URI_AUTHORITY_DEVELOPMENT
            DeploymentEnvironment.STAGING -> URI_AUTHORITY_STAGING
        })
        appendPath("api")
        appendPath(API_VERSION)
    }

    @JvmStatic val retrofit by lazy {
        WebServiceHelper.createWebService<BucketDataSource>(
                "$baseUri/",
                Pair("Content-type", "application/json"))
    }

}