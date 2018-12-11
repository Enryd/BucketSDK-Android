package bucket.sdk.v2.service

import android.os.Build
import bucket.sdk.v2.cache.Credentials
import bucket.sdk.v2.cache.RetailerInfo
import bucket.sdk.v2.cache.Terminal
import bucket.sdk.v2.enum.ExportType
import bucket.sdk.v2.json.events.CreateUpdateEventResponse
import bucket.sdk.v2.json.events.Event
import bucket.sdk.v2.json.export.SendExportBody
import bucket.sdk.v2.json.reporting.*
import bucket.sdk.v2.json.terminal.RegisterTerminalBody
import bucket.sdk.v2.json.transaction.CreateTransactionResponse
import bucket.sdk.v2.json.transaction.TransactionBody
import bucket.sdk.v2.util.ext.asErrorResponse
import io.reactivex.Single
import io.reactivex.Completable
import retrofit2.Response
import java.io.Serializable

/**
 * Bucket repository
 */
internal interface BucketRepository : Serializable {
    /** device/terminal **/
    fun registerTerminal(retailerCode: String, country: String): Completable
    fun getBillDenominations(): Completable

    /** transactionBody **/
    fun createTransaction(
            transactionBody: TransactionBody,
            employeeCode: String? = null,
            eventId: Int? = null): Single<CreateTransactionResponse>
    fun deleteTransaction(customerCode: String): Single<String>
    fun refundTransaction(customerCode: String): Single<String>

    /** events **/
    fun getEvents(
            getEventsBody: Any,
            offset: Int? = null,
            limit: Int? = null): Single<List<Event>>
    fun createUpdateEvent(createUpdateEventBody: Any): Single<CreateUpdateEventResponse>
    fun deleteEvent(id: Int): Single<String>

    /** reporting **/
    fun getReport(
            getReportBody: Any,
            employeeCode: String? = null,
            offset: Int? = null,
            limit: Int? = null): Single<GetReportResponse>
    fun getEventReport(
            getEventReportBody: Any,
            offset: Int? = null,
            limit: Int? = null): Single<GetEventReportResponse>

    /** export **/
    fun sendExport(sendExportBody: SendExportBody,
                   exportType: ExportType?): Single<String>
}

/**
 * Bucket repository
 */

internal class BucketRepositoryImpl(private val bucketDataSource: BucketDataSource) : BucketRepository {

    override fun registerTerminal(retailerCode: String,
                                  country: String): Completable {
        return bucketDataSource.registerTerminal(RegisterTerminalBody(Build.SERIAL), retailerCode, country)
                .map { response ->
                    if (response.isSuccessful) {
                        with(response.body()) {
                            // update cache
                            Terminal.isApproved = isApproved
                            Credentials.apply {
                                this.terminalSecret = apiKey
                                this.retailerCode = retailerCode
                                this.country = country
                            }
                            RetailerInfo.apply {
                                retailerName = this@with.retailerName
                                retailerPhone = this@with.retailerPhone
                                requireEmployeeCode = this@with.requireEmployeeCode ?: false
                                address1 = this@with.address?.address1
                                address2 = this@with.address?.address2
                                address3 = this@with.address?.address3
                                postalCode = this@with.address?.postalCode
                                city = this@with.address?.city
                                state = this@with.address?.state
                            }
                        }

                    } else {
                        throwResponseExceptionMessage(response as Response<*>)
                    }
                }
                .toCompletable()
    }

    override fun getBillDenominations(): Completable {
        return bucketDataSource.getBillDenominations()
                .map { response ->
                    if (response.isSuccessful) {
                        with(response.body()) {
                            // update cache
                            Terminal.usesNaturalChangeFunction = usesNaturalChangeFunction ?: false
                            try {
                                val theDenoms = mutableListOf<Double>()
                                for (i in 0..(denominations.size-1)) {
                                    theDenoms[i] = denominations[i]
                                }
                                var billsString = ""
                                for (bill in theDenoms) {
                                    billsString += if (billsString.isBlank()) bill else ",$bill"
                                }
                                Terminal.billDenoms = billsString

                            } catch (e: Exception) { }
                        }

                    } else {
                        throwResponseExceptionMessage(response as Response<*>)
                    }
                }
                .toCompletable()
    }

    override fun createTransaction(transactionBody: TransactionBody,
                                   employeeCode: String?,
                                   eventId: Int?): Single<CreateTransactionResponse> {
        return bucketDataSource.createTransaction(transactionBody, employeeCode, eventId)
                .map { response ->
                    if (response.isSuccessful) response.body()
                    else throwResponseExceptionMessage(response as Response<*>)
                }
    }

    override fun deleteTransaction(customerCode: String): Single<String> {
        return bucketDataSource.deleteTransaction(customerCode)
                .map { response ->
                    if (response.isSuccessful) response.body().result
                    else throwResponseExceptionMessage(response as Response<*>)
                }
    }

    override fun refundTransaction(customerCode: String): Single<String> {
        return bucketDataSource.refundTransaction(customerCode)
                .map { response ->
                    if (response.isSuccessful) response.body().result
                    else throwResponseExceptionMessage(response as Response<*>)
                }
    }

    override fun getReport(getReportBody: Any,
                           employeeCode: String?,
                           offset: Int?,
                           limit: Int?): Single<GetReportResponse> {
        return bucketDataSource.getReport(
                getReportBody = getReportBody,
                employeeCode = employeeCode,
                offset = offset,
                limit = limit)
                .map { response ->
                    if (response.isSuccessful) response.body()
                    else throwResponseExceptionMessage(response as Response<*>)
                }
    }

    override fun getEventReport(getEventReportBody: Any,
                                offset: Int?,
                                limit: Int?): Single<GetEventReportResponse> {
        return bucketDataSource.getEventReport(
                getEventReportBody = getEventReportBody,
                offset = offset,
                limit = limit)
                .map { response ->
                    if (response.isSuccessful) response.body()
                    else throwResponseExceptionMessage(response as Response<*>)
                }
    }

    override fun getEvents(getEventsBody: Any, offset: Int?, limit: Int?): Single<List<Event>> {
        return bucketDataSource.getEvents(getEventsBody, offset, limit)
                .map { response ->
                    if (response.isSuccessful) response.body().events
                    else throwResponseExceptionMessage(response as Response<*>)
                }
    }

    override fun createUpdateEvent(createUpdateEventBody: Any): Single<CreateUpdateEventResponse> {
        return bucketDataSource.createUpdateEvent(createUpdateEventBody)
                .map { response ->
                    if (response.isSuccessful) response.body()
                    else throwResponseExceptionMessage(response)
                }
    }

    override fun deleteEvent(id: Int): Single<String> {
        return bucketDataSource.deleteEvent(id)
                .map { response ->
                    if (response.isSuccessful) response.body().result
                    else throwResponseExceptionMessage(response)
                }
    }

    override fun sendExport(sendExportBody: SendExportBody,
                            exportType: ExportType?): Single<String> {
        return bucketDataSource.sendExport(sendExportBody, exportType)
                .map { response ->
                    if (response.isSuccessful) response.body().message
                    else throwResponseExceptionMessage(response)
                }
    }

    private fun <T: Any> throwResponseExceptionMessage(response: Response<T>): Nothing {
        // throw message from errorBody or response message
        throw Exception(response.errorBody().asErrorResponse().message ?: response.message())
    }

    private fun <T: Any> throwResponseExceptionCode(response: Response<T>): Nothing {
        // throw code from errorBody or response message
        throw Exception(response.errorBody().asErrorResponse().errorCode ?: response.message())
    }

}