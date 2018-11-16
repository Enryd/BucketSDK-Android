package bucket.sdk

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import bucket.sdk.cache.Preferences
import bucket.sdk.callbacks.*
import bucket.sdk.models.*
import bucket.sdk.extensions.bucketError
import bucket.sdk.models.responses.ReportTransaction
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import org.json.JSONObject
import kotlin.collections.ArrayList

class Bucket {

    companion object {

        @JvmStatic var appContext : Context? = null

        @JvmStatic var environment = DeploymentEnvironment.Development
//        @JvmStatic private var denoms : List<Double> = listOf(100.00, 50.00, 20.00, 10.00, 5.00, 2.00, 1.00)
        @JvmStatic private var denoms = listOf<Double>()
            get() {
                val billsList = mutableListOf<Double>()
                for (double in Preferences.billDenoms.split(",")) {
                    billsList.add(double.toDouble())
                }
                return billsList
            }

        @JvmStatic private var usesNaturalChangeFunction : Boolean
            get() { return Credentials.sharedPrefs?.getBoolean("USES_NATURAL_CHANGE", false) ?: false }
            set(value) {
                val editor = Credentials.sharedPrefs?.edit()
                editor?.putBoolean("USES_NATURAL_CHANGE", value)
                editor?.apply()
            }

        @JvmStatic fun bucketAmount(changeDueBack: Double): Double {
            var bucketAmount = changeDueBack
            if (usesNaturalChangeFunction) {
                // Make sure this is ordered by the amount
                val filteredDenoms = denoms.filter { it <= changeDueBack }

                // These values should already be descended from 10000 down to 100.
                filteredDenoms.forEach { denomination ->
                    bucketAmount = (bucketAmount % denomination)
                }

            } else {
                while (bucketAmount > 1.00) bucketAmount = (bucketAmount % 1.00)

            }
            return bucketAmount
        }

        @SuppressLint("CheckResult")
        @JvmStatic fun fetchBillDenominations(callback: BillDenomination?) {

            var shouldReturn = false
            val retailerCode = Credentials.retailerCode()
            val terminalSecret = Credentials.terminalSecret()

            if (retailerCode.isNullOrEmpty() || terminalSecret.isNullOrEmpty()) {
                shouldReturn = true
                callback?.didError(Error.unauthorized)
            }

            val countryCode = Credentials.countryCode()
            if (countryCode.isNullOrEmpty()) {
                shouldReturn = true
                callback?.didError(Error.invalidCountryCode)
            }

            if (shouldReturn) return

            val theURL = environment.billDenoms.build().toString()

            theURL.httpPost().header(Pair("countryId", countryCode!!)).responseJson {
                _, response, result ->

                when (result) {
                    is Result.Success -> {
                        val responseJson = result.value.obj()
                        val denominations = responseJson.optJSONArray("denominations")
                        usesNaturalChangeFunction = responseJson.optBoolean("usesNaturalChangeFunction", false)
                        denominations?.let {
                            // Create our list of denominations:
                            val theDenoms : MutableList<Double> = ArrayList()
                            for (i in 0..(it.length()-1)) {
                                theDenoms[i] = it.getDouble(i)
                            }
                            var billsString = ""
                            for (bill in theDenoms) {
                                billsString += if (billsString.isBlank()) bill else ",$bill"
                            }
                            Preferences.billDenoms = billsString
//                            Bucket.denoms = theDenoms
                        }
                        callback?.setBillDenoms()
                    }
                    is Result.Failure -> {
                        val error = response.bucketError
                        callback?.didError(error)
                    }
                }
            }

//            BucketService.retrofit.billDenominations(
//                    terminalSecret = terminalSecret!!,
//                    countryId = countryCode!!)
//                    .map { response ->
//                        if (response.isSuccessful) {
//                            val denominations = response.body().denominations
//                            usesNaturalChangeFunction = response.body().usesNaturalChangeFunction
//                            denominations.let {
//                                // Create our list of denominations:
//                                val theDenoms : MutableList<Double> = ArrayList()
//                                for (i in 0..(it.size-1)) {
//                                    theDenoms[i] = it[i]
//                                }
//                                Bucket.denoms = theDenoms
//                            }
//                            callback?.setBillDenoms()
//
//                        } else {
//                            val errorCode = response.body().errorCode
//                            val errorMessage = response.body().message
//                            callback?.didError(Error(errorMessage ?: "Unknown API Error", errorCode ?: "Unknown Error Code", response.code()))
//                        }
//                    }
        }

        @SuppressLint("CheckResult")
        @JvmStatic fun registerTerminal(countryCode: String, callback: RegisterTerminal?) {

            val retailerCode = Credentials.retailerCode()

            if (retailerCode.isNullOrEmpty()) {
                callback?.didError(Error("Please check your retailer id", "InvalidRetailer", 401))
            }

//            BucketService.retrofit.registerTerminal(
//                    countryId = countryCode,
//                    retailerId = retailerCode!!,
//                    registerTerminalBody = RegisterTerminalBody(terminalId = Build.SERIAL))
//                    .map { response ->
//                        if (response.isSuccessful) {
//                            val apiKey = response.body().apiKey
//                            val isApproved = response.body().isApproved
//                            // Set the terminal secret:
//                            Credentials.setCountryCode(countryCode)
//                            Credentials.setTerminalSecret(apiKey)
//                            callback?.success(isApproved)
//
//                        } else {
//                            val errorCode = response.body().errorCode
//                            val errorMessage = response.body().message
//                            callback?.didError(Error(errorMessage ?: "Unknown API Error", errorCode ?: "Unknown Error Code", response.code()))
//                        }
//                    }

            val json = JSONObject().apply { put("terminalId", Build.SERIAL) }

            val theURL = environment.registerTerminal.build().toString()

            theURL.httpPost()
                    .body(json.toString())
                    .header(Pair("retailerId", retailerCode!!))
                    .header(Pair("countryId", countryCode))
                    .responseJson { _, response, result ->

                        when (result) {
                            is Result.Failure -> {
                                val error = response.bucketError
                                callback?.didError(error)
                            }
                            is Result.Success -> {
                                Log.e("response", response.toString())
                                val responseJson = result.value.obj()
                                val apiKey = responseJson.optString("apiKey", "")
                                val isApproved = responseJson.optBoolean("isApproved", false)
                                val name = responseJson.optString("retailerName", "")
                                val phone = responseJson.optString("retailerPhone", "")
                                // address assemble
                                val addressObject = responseJson.optJSONObject("address") ?: null
                                val address1 = addressObject?.optString("address1", "") ?: ""
                                val address2 = addressObject?.optString("address2", "") ?: ""
                                val address3 = addressObject?.optString("address3", "") ?: ""
                                val postalCode = addressObject?.optString("postalCode", null)
                                val city = addressObject?.optString("city", "") ?: ""
                                val state = addressObject?.optString("state", "") ?: ""
                                var address = ""
                                if (address1 != null) address += "$address1\n"
                                if (address2 != null) address += "$address2\n"
                                if (address3 != null) address += "$address3\n"
                                if (city != null) address += city
                                if (state != null) address += ", $state"
                                if (postalCode != null) address += ", $postalCode"

                                // Set the terminal credentials:
                                Credentials.apply {
                                    setCountryCode(countryCode)
                                    setTerminalSecret(apiKey)
                                    setAddress1(address1)
                                    setAddress2(address2)
                                    setAddress3(address3)
                                    setPostalCode(postalCode)
                                    setCity(city)
                                    setState(state)
                                    setAddress(address)
                                    setPhone(phone)
                                    setName(name)
                                }
                                callback?.success(isApproved)
                            }
                        }
                    }
        }

        @JvmStatic fun reporting(startTime: String, endTime: String, employeeId: String = "", terminalId: String?, callback: Reporting?) {

            val url = Bucket.environment.report.build().toString()

            val jsonObject = JSONObject()

            jsonObject.put("start", startTime)
            jsonObject.put("end", endTime)
            if (!terminalId.isNullOrEmpty()) jsonObject.put("terminalId", terminalId)

            url.httpPost()
                    .header(Pair("x-functions-key", Credentials.terminalSecret()!!))
                    .header(Pair("retailerId", Credentials.retailerCode()!!))
                    .header(Pair("countryId", Credentials.countryCode()!!))
                    .header(Pair("employeeId", employeeId))
                    .body(jsonObject.toString()).responseJson {
                        request, response, result ->
                        Log.d("bucket.sdk REQUEST: ", request.toString())
                        when (result) {
                            is Result.Success -> {
                                val responseObj = result.value.obj()
                                val bucketTotal = responseObj.optDouble("bucketTotal")
                                val transactionsArray = responseObj.optJSONArray("transactions")
                                val transactionsList = ArrayList<ReportTransaction>()
                                for (i in 0 until transactionsArray.length()) {
                                    val transactionObject = transactionsArray.getJSONObject(i)
                                    with(transactionObject) {
                                        transactionsList.add(
                                                ReportTransaction(
                                                        bucketTransactionId = optInt("bucketTransactionId"),
                                                        created = optString("created"),
                                                        amount = optDouble("amount"),
                                                        totalTransactionAmount = optDouble("totalTransactionAmount"),
                                                        customerCode = optString("customerCode"),
                                                        disputed = optString("disputed"),
                                                        disputedBy = optString("disputedBy"),
                                                        locationId = optString("locationId"),
                                                        clientTransactionId = optString("clientTransactionId"),
                                                        terminalId = optString("terminalId")))
                                    }
                                }

                                callback?.success(bucketTotal, transactionsList)
                            }
                            is Result.Failure -> {
                                callback?.didError(response.bucketError)
                            }
                        }
                    }
        }

    }


}