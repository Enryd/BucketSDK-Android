package bucket.sdk.deprecated.models

// Bucket Packages:
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import bucket.sdk.deprecated.Bucket
import bucket.sdk.deprecated.Credentials
import bucket.sdk.deprecated.annotations.*
import bucket.sdk.deprecated.callbacks.*
import bucket.sdk.deprecated.extensions.*
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import org.json.JSONObject
import java.net.URL

@Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
class Transaction(var amount: Double, var totalTransactionAmount: Double?, var employeeId: String? = null, var clientTransactionId: String? = null) {

    // This is the primary key for the transaction in our db, as annotated:
    @PrimaryKey var bucketTransactionId : Int? = null
    var customerCode                    : String?  = null
    var qrCodeContent                   : URL? = null

    // The rest of these fields are specified by the retailer:
    var locationId                      : String? = null

    private fun updateWith(updateJSON: JSONObject?) {

        if (updateJSON.isNil) return

        this.customerCode = updateJSON!!.optString("customerCode", null)
        this.locationId = updateJSON.optString("locationId", null)
        this.bucketTransactionId = updateJSON.optInt("bucketTransactionId")
        this.qrCodeContent = updateJSON.getURL("qrCodeContent")
        this.totalTransactionAmount = updateJSON.optDouble("totalTransactionAmount", 0.0)
    }

//    private fun updateWith(transactionResponse: CreateTransactionResponse?) {
//        if (transactionResponse.isNil) return
//
//        customerCode = transactionResponse?.customerCode
//        locationId = transactionResponse?.locationId
//        bucketTransactionId = transactionResponse?.bucketTransactionId
//        qrCodeContent = URL(transactionResponse?.qrCodeContent)
//        totalTransactionAmount = transactionResponse?.amount ?: 0.00
//    }

    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    fun toJSON(): JSONObject {

        val obj = JSONObject()

        // We will always set the amount & clientTransactionId when sending the JSON:
        obj.put("amount", amount)
        obj.put("clientTransactionId", clientTransactionId)
        obj.put("totalTransactionAmount", totalTransactionAmount)

        // The fields that may not be set:
        if (!locationId.isNil) { obj.put("locationId", locationId!!) }
        if (!customerCode.isNil) { obj.put("customerCode", customerCode!!) }
        if (!qrCodeContent.isNil) { obj.put("qrCodeContent", qrCodeContent!!) }

        return obj
    }

    companion object {
        @SuppressLint("CheckResult")
        @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
        @JvmStatic fun deleteBy(customerCode: String, callback: DeleteTransaction?) {

            // Get the client id & client secret for this retailer:
            val retailerCode = Credentials.retailerCode()
            val terminalSecret = Credentials.terminalSecret()
            val countryCode = Credentials.countryCode()

            var shouldIReturn = false
            if (retailerCode.isNullOrEmpty() || terminalSecret.isNullOrEmpty()) {
                shouldIReturn = true
                callback?.didError(Error.unauthorized)
            }
            if (countryCode.isNullOrEmpty()) {
                shouldIReturn = true
                callback?.didError(Error.invalidCountryCode)
            }

            if (shouldIReturn) return

            val url = Bucket.environment.transaction.appendPath(customerCode).build().toString()

            url.httpDelete()
                    .header(Pair("x-functions-key", terminalSecret!!))
                    .header(Pair("retailerCode", retailerCode!!))
                    .header(Pair("countryId", countryCode!!))
                    .header(Pair("terminalCode", Build.SERIAL)).responseJson { _, response, result ->
                        when (result) {
                            is Result.Success -> {
                                callback?.transactionDeleted()
                            }
                            is Result.Failure -> {
                                callback?.didError(response.bucketError)
                            }
                        }
                    }

//            BucketService.retrofit.deleteTransaction(
//                    terminalSecret = terminalSecret!!,
//                    retailerCode = retailerCode!!,
//                    countryId = country!!,
//                    terminalCode = Build.SERIAL,
//                    customerCode = customerCode)
//                    .map { response ->
//                        if (response.isSuccessful) {
//                            callback?.transactionDeleted()
//
//                        } else {
//                            val errorCode = response.body().errorCode
//                            val errorMessage = response.body().message
//                            callback?.didError(Error(errorMessage ?: "Unknown API Error", errorCode ?: "Unknown Error Code", response.code()))
//                        }
//                    }

        }
    }


    @SuppressLint("CheckResult")
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    fun delete(callback: DeleteTransaction?) {

        // Get the client id & client secret for this retailer:
        val retailerCode = Credentials.retailerCode()
        val terminalSecret = Credentials.terminalSecret()
        val countryCode = Credentials.countryCode()

        var shouldIReturn = false
        if (retailerCode.isNullOrEmpty() || terminalSecret.isNullOrEmpty()) {
            shouldIReturn = true
            callback?.didError(Error.unauthorized)
        }
        if (countryCode.isNullOrEmpty()) {
            shouldIReturn = true
            callback?.didError(Error.invalidCountryCode)
        }

        if (shouldIReturn) return

        val url = Bucket.environment.transaction.appendPath(customerCode).build().toString()

        url.httpDelete()
                .header(Pair("x-functions-key", terminalSecret!!))
                .header(Pair("retailerCode", retailerCode!!))
                .header(Pair("countryId", countryCode!!))
                .header(Pair("terminalCode", Build.SERIAL)).responseJson {
                    _, response, result ->
                    when (result) {
                        is Result.Success -> {
                            callback?.transactionDeleted()
                        }
                        is Result.Failure -> {
                            callback?.didError(response.bucketError)
                        }
                    }
                }

//        BucketService.retrofit.deleteTransaction(
//                terminalSecret = terminalSecret!!,
//                retailerCode = retailerCode!!,
//                countryId = country!!,
//                terminalCode = Build.SERIAL,
//                customerCode = customerCode!!)
//                .map { response ->
//                    if (response.isSuccessful) {
//                        callback?.transactionDeleted()
//
//                    } else {
//                        val errorCode = response.body().errorCode
//                        val errorMessage = response.body().message
//                        callback?.didError(Error(errorMessage ?: "Unknown API Error", errorCode ?: "Unknown Error Code", response.code()))
//                    }
//                }

    }

    @SuppressLint("CheckResult")
    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    fun create(callback: CreateTransaction?) {

        // Get the client id & client secret for this retailer:
        val retailerCode = Credentials.retailerCode()
        val terminalSecret = Credentials.terminalSecret()
        val countryCode = Credentials.countryCode()

        var shouldIReturn = false
        if (retailerCode.isNullOrEmpty() || terminalSecret.isNullOrEmpty()) {
            shouldIReturn = true
            callback?.didError(Error.unauthorized)
        }
        if (countryCode.isNullOrEmpty()) {
            shouldIReturn = true
            callback?.didError(Error.invalidCountryCode)
        }

        if (shouldIReturn) return

        val jsonBody = this.toJSON()

        val url = Bucket.environment.transaction.build().toString()

        url.httpPost()
                .header(Pair("x-functions-key", terminalSecret!!))
                .header(Pair("retailerCode", retailerCode!!))
                .header(Pair("countryId", countryCode!!))
                .header(Pair("terminalCode", Build.SERIAL))
                .header(Pair("employeeId", employeeId ?: ""))
                .body(jsonBody.toString()).responseJson {
                    request, response, result ->
                    Log.d("bucket.sdk REQUEST: ", request.toString())
                    when (result) {
                        is Result.Success -> {
                            this@Transaction.updateWith(result.value.obj())
                            callback?.transactionCreated()
                        }
                        is Result.Failure -> {
                            callback?.didError(response.bucketError)
                        }
                    }
                }

//        BucketService.retrofit.createTransaction(
//                terminalSecret = terminalSecret!!,
//                retailerCode = retailerCode!!,
//                countryId = country!!,
//                terminalCode = Build.SERIAL,
//                transaction = this)
//                .map { response ->
//                    if (response.isSuccessful) {
//                        Log.e("response", "${response.body()}")
//                        updateWith(response.body())
//                        callback?.transactionCreated()
//
//                    } else {
//                        val errorCode = response.body().errorCode
//                        val errorMessage = response.body().message
//                        callback?.didError(Error(errorMessage ?: "Unknown API Error", errorCode ?: "Unknown Error Code", response.code()))
//                    }
//                }

    }

    @Deprecated("This is an old unmaintained version. Please check the bucket sdk documentation.")
    fun generateQRCode(): Bitmap? {
        val multiFormatWriter = MultiFormatWriter()
        var bitmap: Bitmap? = null
        try {
            val bitMatrix = multiFormatWriter.encode(qrCodeContent.toString(), BarcodeFormat.QR_CODE, 200, 200)
            bitmap = BarcodeEncoder().createBitmap(bitMatrix)

        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return bitmap
    }

}