package bucket.sdk.v2

import bucket.sdk.v2.json.events.Event
import bucket.sdk.v2.json.reporting.GetReportResponse
import bucket.sdk.v2.json.transaction.CreateTransactionResponse

interface Callback {
    interface RegisterTerminal {
        fun onSuccess()
        fun onError(error: String)
    }
    interface GetBillDenominations {
        fun onSuccess()
        fun onError(error: String)
    }
    interface CreateTransaction {
        fun onSuccess(createTransactionResponse: CreateTransactionResponse)
        fun onError(error: String)
    }
    interface DeleteTransaction {
        fun onSuccess(message: String)
        fun onError(error: String)
    }
    interface RefundTransaction {
        fun onSuccess(message: String)
        fun onError(error: String)
    }
    interface GetReport {
        fun onSuccess(getReportResponse: GetReportResponse)
        fun onError(error: String)
    }
    interface GetEvents {
        fun onSuccess(events: List<Event>)
        fun onError(error: String)
    }
    interface CreateEvent {
        fun onSuccess(message: String)
        fun onError(error: String)
    }
    interface UpdateEvent {
        fun onSuccess(message: String)
        fun onError(error: String)
    }
    interface DeleteEvent {
        fun onSuccess(message: String)
        fun onError(error: String)
    }
}