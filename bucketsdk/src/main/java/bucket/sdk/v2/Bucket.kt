package bucket.sdk.v2

import android.annotation.SuppressLint
import android.content.Context
import bucket.sdk.cache.Preferences
import bucket.sdk.v2.cache.Terminal
import bucket.sdk.v2.json.events.*
import bucket.sdk.v2.json.reporting.ReportDateStringsBody
import bucket.sdk.v2.json.reporting.ReportDayStringBody
import bucket.sdk.v2.json.reporting.ReportEpochIntegersBody
import bucket.sdk.v2.json.terminal.RegisterTerminalBody
import bucket.sdk.v2.json.transaction.TransactionBody
import bucket.sdk.v2.service.BucketRepositoryImpl
import bucket.sdk.v2.service.BucketService
import bucket.sdk.v2.util.ext.with
import bucket.sdk.v2.util.rx.ApplicationSchedulerProvider
import com.chibatching.kotpref.Kotpref
import com.google.gson.JsonObject
import com.pawegio.kandroid.e

class Bucket {

    companion object {

        @JvmStatic var appContext : Context? = null
            set(context) {
                context?.let { Kotpref.init(context) }
                field = context
            }

        /**
         * @see DeploymentEnvironment
         */
        @JvmStatic var environment = DeploymentEnvironment.DEVELOPMENT

        /** Cached bill denominations **/
        @JvmStatic private var denoms = listOf<Double>()
            get() {
                val billsList = mutableListOf<Double>()
                for (double in Preferences.billDenoms.split(",")) {
                    billsList.add(double.toDouble())
                }
                return billsList
            }

        /**
         * Amount to be bucketed
         * @param changeDueBack original amount owed to customer
         */
        @JvmStatic fun bucketAmount(changeDueBack: Double): Double {
            var bucketAmount = changeDueBack
            if (Terminal.usesNaturalChangeFunction) {
                // Remove bills higher than the amount
                // These values should already be descended from 10000 down to 100.
                denoms.filter { it <= changeDueBack }.forEach { denomination ->
                    bucketAmount = (bucketAmount % denomination)
                }

            } else {
                while (bucketAmount > 1.00) bucketAmount = (bucketAmount % 1.00)
            }
            return bucketAmount
        }

        /**
         * Register terminal
         * @param retailerCode This is the retailer code creating the transaction.
         * @param country This is the numeric country id, or the alpha two letter country code.
         * @param callback
         */
        @SuppressLint("CheckResult")
        @JvmStatic fun registerTerminal(retailerCode: String,
                                        country: String,
                                        callback: Callback.RegisterTerminal) {
            // request
            BucketRepositoryImpl(BucketService.retrofit).registerTerminal(retailerCode, country)
                    .with(ApplicationSchedulerProvider())
                    .subscribe(
                            { callback.onSuccess() },
                            { error -> callback.onError(error.message.toString()) })
        }

        /**
         * Get bill denominations
         * @param callback
         */
        @SuppressLint("CheckResult")
        @JvmStatic fun getBillDenominations(callback: Callback.GetBillDenominations) {
            e("getBillDenominations")
            // request
            BucketRepositoryImpl(BucketService.retrofit).getBillDenominations()
                    .with(ApplicationSchedulerProvider())
                    .subscribe(
                            { callback.onSuccess() },
                            { error -> callback.onError(error.message.toString()) })
        }

        /**
         * Create transaction
         * @param transactionBody Transaction request params
         * @param employeeCode This is the four digit code assigned to the employee.
         * | This only required when the retailer turns on employee codes.
         * @param eventId This is the eventId. Please make sure to create an event and
         * | use that id here if you want to associate the transaction with an event.
         * @param callback
         */
        @SuppressLint("CheckResult")
        @JvmStatic fun createTransaction(transactionBody: TransactionBody,
                                         employeeCode: String? = null,
                                         eventId: Int? = null,
                                         callback: Callback.CreateTransaction) {
            // request
            BucketRepositoryImpl(BucketService.retrofit).createTransaction(transactionBody, employeeCode, eventId)
                    .with(ApplicationSchedulerProvider())
                    .subscribe(
                            { response -> callback.onSuccess(response) },
                            { error -> callback.onError(error.message.toString()) })
        }

        /**
         * Delete transaction
         * @param customerCode This is the customer code for the created transaction.
         * @param callback
         */
        @SuppressLint("CheckResult")
        @JvmStatic fun deleteTransaction(customerCode: String, callback: Callback.DeleteTransaction) {
            // request
            BucketRepositoryImpl(BucketService.retrofit).deleteTransaction(customerCode)
                    .with(ApplicationSchedulerProvider())
                    .subscribe(
                            { message -> callback.onSuccess(message) },
                            { error -> callback.onError(error.message.toString()) })
        }

        /**
         * Refund transaction
         * @param customerCode This is the customer code for the created transaction.
         * @param callback
         */
        @SuppressLint("CheckResult")
        @JvmStatic fun refundTransaction(customerCode: String, callback: Callback.RefundTransaction) {
            // request
            BucketRepositoryImpl(BucketService.retrofit).refundTransaction(customerCode)
                    .with(ApplicationSchedulerProvider())
                    .subscribe(
                            { message -> callback.onSuccess(message) },
                            { error -> callback.onError(error.message.toString()) })
        }

        /**
         * Get transactions report with date string range
         * @param getReportBody Get report request params
         * @param callback
         */
        @SuppressLint("CheckResult")
        @JvmStatic fun getReport(getReportBody: ReportDateStringsBody,
                                 employeeCode: String? = null,
                                 offset: Int? = null,
                                 limit: Int? = null,
                                 callback: Callback.GetReport) {
            // request
            BucketRepositoryImpl(BucketService.retrofit).getReport(getReportBody, employeeCode, offset, limit)
                    .with(ApplicationSchedulerProvider())
                    .subscribe(
                            { getReportResponse -> callback.onSuccess(getReportResponse)},
                            { error -> callback.onError(error.message.toString()) })
        }

        /**
         * Get transactions report with epoch integer range
         * @param getReportBody Get report request params
         * @param callback
         */
        @SuppressLint("CheckResult")
        @JvmStatic fun getReport(getReportBody: ReportEpochIntegersBody,
                                 employeeCode: String? = null,
                                 offset: Int? = null,
                                 limit: Int? = null,
                                 callback: Callback.GetReport) {
            // request
            BucketRepositoryImpl(BucketService.retrofit).getReport(getReportBody, employeeCode, offset, limit)
                    .with(ApplicationSchedulerProvider())
                    .subscribe(
                            { getReportResponse -> callback.onSuccess(getReportResponse)},
                            { error -> callback.onError(error.message.toString()) })
        }

        /**
         * Get transactions report for the day
         * @param getReportBody Get report request params
         * @param callback
         */
        @SuppressLint("CheckResult")
        @JvmStatic fun getReport(getReportBody: ReportDayStringBody,
                                 employeeCode: String? = null,
                                 offset: Int? = null,
                                 limit: Int? = null,
                                 callback: Callback.GetReport) {
            // request
            BucketRepositoryImpl(BucketService.retrofit).getReport(getReportBody, employeeCode, offset, limit)
                    .with(ApplicationSchedulerProvider())
                    .subscribe(
                            { getReportResponse -> callback.onSuccess(getReportResponse)},
                            { error -> callback.onError(error.message.toString()) })
        }

        /**
         * Get list of events with date string range
         * @param getEventsBody Get events request params
         * @param offset This is the offset of the request. This will position the array of events for the request.
         * @param limit This is the limit of the request. This limits the number of events
         * | that are returned in the events array. The maximum limit for this endpoint is 1000.
         * @param callback
         */
//        @SuppressLint("CheckResult")
//        @JvmStatic fun getEvents(getEventsBody: EventsDateStringsBody,
//                                 offset: Int? = null,
//                                 limit: Int? = null,
//                                 callback: Callback.GetEvents) {
//            // request
//            BucketRepositoryImpl(BucketService.retrofit).getEvents(getEventsBody, offset, limit)
//                    .with(ApplicationSchedulerProvider())
//                    .subscribe(
//                            { events -> callback.onSuccess(events)},
//                            { error -> callback.onError(error.message.toString()) })
//        }

        /**
         * Get list of events with epoch integer range
         * @param getEventsBody Get events request params
         * @param offset This is the offset of the request. This will position the array of events for the request.
         * @param limit This is the limit of the request. This limits the number of events
         * | that are returned in the events array. The maximum limit for this endpoint is 1000.
         * @param callback
         */
//        @SuppressLint("CheckResult")
//        @JvmStatic fun getEvents(getEventsBody: EventsEpochIntegersBody,
//                                 offset: Int? = null,
//                                 limit: Int? = null,
//                                 callback: Callback.GetEvents) {
//            // request
//            BucketRepositoryImpl(BucketService.retrofit).getEvents(getEventsBody, offset, limit)
//                    .with(ApplicationSchedulerProvider())
//                    .subscribe(
//                            { events -> callback.onSuccess(events)},
//                            { error -> callback.onError(error.message.toString()) })
//        }

        /**
         * Get list of events with id
         * @param id Event id
         * @param offset This is the offset of the request. This will position the array of events for the request.
         * @param limit This is the limit of the request. This limits the number of events
         * | that are returned in the events array. The maximum limit for this endpoint is 1000.
         * @param callback
         */
        @SuppressLint("CheckResult")
        @JvmStatic fun getEventWithId(id: Int,
                                      offset: Int? = null,
                                      limit: Int? = null,
                                      callback: Callback.GetEvents) {
            // request body
            val getEventsBody = EventsIdBody(id)
            // request
            BucketRepositoryImpl(BucketService.retrofit).getEvents(getEventsBody, offset, limit)
                    .with(ApplicationSchedulerProvider())
                    .subscribe(
                            { events -> callback.onSuccess(events)},
                            { error -> callback.onError(error.message.toString()) })
        }

        /**
         * Create an event
         * @param createEventBody Create event request params
         * @param callback
         */
        @SuppressLint("CheckResult")
        @JvmStatic fun createEvent(createEventBody: CreateEventBody,
                                   callback: Callback.CreateEvent) {
            // request
            BucketRepositoryImpl(BucketService.retrofit).createUpdateEvent(createEventBody)
                    .with(ApplicationSchedulerProvider())
                    .subscribe(
                            { message -> callback.onSuccess(message) },
                            { error -> callback.onError(error.message.toString()) })
        }

        /**
         * Update an event
         * @param updateEventBody Update event request params
         * @param callback
         */
        @SuppressLint("CheckResult")
        @JvmStatic fun updateEvent(updateEventBody: UpdateEventBody,
                                   callback: Callback.UpdateEvent) {
            // request
            BucketRepositoryImpl(BucketService.retrofit).createUpdateEvent(updateEventBody)
                    .with(ApplicationSchedulerProvider())
                    .subscribe(
                            { message -> callback.onSuccess(message) },
                            { error -> callback.onError(error.message.toString()) })
        }

        /**
         * Delete an event
         * @param id This is the eventId. This is required to delete the event.
         * @param callback
         */
        @SuppressLint("CheckResult")
        @JvmStatic fun deleteEvent(id: Int,
                                   callback: Callback.DeleteEvent) {
            // request
            BucketRepositoryImpl(BucketService.retrofit).deleteEvent(id)
                    .with(ApplicationSchedulerProvider())
                    .subscribe(
                            { message -> callback.onSuccess(message) },
                            { error -> callback.onError(error.message.toString()) })
        }

    }

}