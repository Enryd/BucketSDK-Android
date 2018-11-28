package bucket.sdk.v2.util.ext

import bucket.sdk.v2.json.ErrorResponse
import com.google.gson.Gson
import okhttp3.ResponseBody

fun ResponseBody.asErrorResponse(): ErrorResponse = Gson().fromJson(string(), ErrorResponse::class.java)