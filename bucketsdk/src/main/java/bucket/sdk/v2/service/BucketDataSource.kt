package bucket.sdk.v2.service

import android.annotation.SuppressLint
import android.os.Build
import bucket.sdk.v2.cache.Credentials
import bucket.sdk.v2.enum.ExportType
import bucket.sdk.v2.json.events.*
import bucket.sdk.v2.json.export.SendExportBody
import bucket.sdk.v2.json.export.SendExportResponse
import bucket.sdk.v2.json.reporting.*
import bucket.sdk.v2.json.terminal.RegisterTerminalBody
import bucket.sdk.v2.json.terminal.GetBillDenominationsResponse
import bucket.sdk.v2.json.terminal.RegisterTerminalResponse
import bucket.sdk.v2.json.transaction.TransactionBody
import bucket.sdk.v2.json.transaction.CreateTransactionResponse
import bucket.sdk.v2.json.transaction.DeleteTransactionResponse
import bucket.sdk.v2.json.transaction.RefundTransactionResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

/**
 * Bucket datasource - Retrofit tagged
 */
@SuppressLint("HardwareIds")
interface BucketDataSource {

    /** DEVICE/TERMINAL **/

    @POST("registerterminal")
    fun registerTerminal(@Body registerTerminalBody: RegisterTerminalBody,
                         @Header("retailerCode") retailerCode: String? = Credentials.retailerCode,
                         @Header("country") country: String? = Credentials.country)
            : Single<Response<RegisterTerminalResponse>>

    @POST("billDenoms")
    fun getBillDenominations(@Header("x-functions-key") terminalSecret: String? = Credentials.terminalSecret,
                             @Header("country") country: String? = Credentials.country)
            : Single<Response<GetBillDenominationsResponse>>

    /** TRANSACTIONS **/

    @POST("transaction")
    fun createTransaction(@Body transactionBody: TransactionBody,
                          @Header("employeeCode") employeeCode: String? = null,
                          @Header("eventId") eventId: Int? = null,
                          @Header("x-functions-key") terminalSecret: String? = Credentials.terminalSecret,
                          @Header("retailerCode") retailerCode: String? = Credentials.retailerCode,
                          @Header("terminalCode") terminalCode: String? = Build.SERIAL,
                          @Header("country") country: String? = Credentials.country)
            : Single<Response<CreateTransactionResponse>>

    @DELETE("transaction/{customerCode}")
    fun deleteTransaction(@Path("customerCode") customerCode: String,
                          @Header("x-functions-key") terminalSecret: String? = Credentials.terminalSecret,
                          @Header("retailerCode") retailerCode: String? = Credentials.retailerCode,
                          @Header("terminalCode") terminalCode: String? = Build.SERIAL,
                          @Header("country") country: String? = Credentials.country)
            : Single<Response<DeleteTransactionResponse>>

    @PATCH("transaction/{customerCode}")
    fun refundTransaction(@Path("customerCode") customerCode: String,
                          @Header("x-functions-key") terminalSecret: String? = Credentials.terminalSecret,
                          @Header("retailerCode") retailerCode: String? = Credentials.retailerCode,
                          @Header("terminalCode") terminalCode: String? = Build.SERIAL,
                          @Header("country") country: String? = Credentials.country)
            : Single<Response<RefundTransactionResponse>>

    /** EVENTS **/

    @POST("events")
    fun getEvents(@Body getEventsBody: Any,
                  @Query("offset") offset: Int? = null,
                  @Query("limit") limit: Int? = null,
                  @Header("x-functions-key") terminalSecret: String? = Credentials.terminalSecret,
                  @Header("retailerCode") retailerCode: String? = Credentials.retailerCode,
                  @Header("terminalCode") terminalCode: String? = Build.SERIAL,
                  @Header("country") country: String? = Credentials.country)
            : Single<Response<GetEventsResponse>>

    @PUT("event")
    fun createUpdateEvent(@Body createUpdateEventBody: Any,
                          @Header("x-functions-key") terminalSecret: String? = Credentials.terminalSecret,
                          @Header("retailerCode") retailerCode: String? = Credentials.retailerCode,
                          @Header("terminalCode") terminalCode: String? = Build.SERIAL,
                          @Header("country") country: String? = Credentials.country)
            : Single<Response<CreateUpdateEventResponse>>

    @DELETE("event/{id}")
    fun deleteEvent(@Path("id") id: Int,
                    @Header("x-functions-key") terminalSecret: String? = Credentials.terminalSecret,
                    @Header("retailerCode") retailerCode: String? = Credentials.retailerCode)
            : Single<Response<DeleteEventResponse>>

    /** REPORTING **/

    @POST("report")
    fun getReport(@Body getReportBody: Any,
                  @Header("employeeCode") employeeCode: String? = null,
                  @Query("offset") offset: Int? = null,
                  @Query("limit") limit: Int? = null,
                  @Header("x-functions-key") terminalSecret: String? = Credentials.terminalSecret,
                  @Header("retailerCode") retailerCode: String? = Credentials.retailerCode,
                  @Header("terminalCode") terminalCode: String? = Build.SERIAL,
                  @Header("country") country: String? = Credentials.country)
            : Single<Response<GetReportResponse>>

    @POST("report/events")
    fun getEventReport(@Body getEventReportBody: Any,
                       @Query("offset") offset: Int? = null,
                       @Query("limit") limit: Int? = null,
                       @Header("x-functions-key") terminalSecret: String? = Credentials.terminalSecret,
                       @Header("retailerCode") retailerCode: String? = Credentials.retailerCode,
                       @Header("terminalCode") terminalCode: String? = Build.SERIAL,
                       @Header("country") country: String? = Credentials.country)
            : Single<Response<GetEventReportResponse>>

    /** EXPORT **/

    @POST("export/events")
    fun sendExport(@Body sendExportBody: SendExportBody,
                   @Query("exportType") exportType: ExportType? = ExportType.CSV,
                   @Header("x-functions-key") terminalSecret: String? = Credentials.terminalSecret,
                   @Header("retailerCode") retailerCode: String? = Credentials.retailerCode,
                   @Header("terminalCode") terminalCode: String? = Build.SERIAL,
                   @Header("country") country: String? = Credentials.country)
            : Single<Response<SendExportResponse>>

}
