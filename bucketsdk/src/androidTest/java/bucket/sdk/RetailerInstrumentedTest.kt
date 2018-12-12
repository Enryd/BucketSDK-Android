package bucket.sdk

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import bucket.sdk.v2.Bucket
import bucket.sdk.v2.Callback
import bucket.sdk.v2.cache.Credentials
import bucket.sdk.v2.json.reporting.GetReportResponse
import bucket.sdk.v2.json.reporting.ReportDateStringsBody
import bucket.sdk.v2.json.transaction.CreateTransactionResponse
import bucket.sdk.v2.json.transaction.TransactionBody
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.FixMethodOrder
import org.junit.runners.MethodSorters

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class RetailerInstrumentedTest {

    @Test fun test01UseAppContext() {
        Bucket.appContext = InstrumentationRegistry.getTargetContext()
        Credentials.terminalSecret
        assertTrue(true)
        TestCache.customerCode = ""
    }

    @Test fun test02RegisteringDevice() {
        val syncObject = Object()
        Bucket.registerTerminal("bckt-1", "us", object : Callback.RegisterTerminal {
            override fun onSuccess() {
                assert(true)
                synchronized (syncObject) { syncObject.notify() }
            }
            override fun onError(error: String) {
                assertTrue(false)
                synchronized (syncObject) { syncObject.notify() }
            }
        })
        synchronized (syncObject) { syncObject.wait() }
    }

    @Test fun test03GetBillDenominations() {
        val syncObject = Object()
        Bucket.getBillDenominations(object : Callback.GetBillDenominations {
            override fun onSuccess() {
                assertTrue(true)
                synchronized (syncObject) { syncObject.notify() }
            }

            override fun onError(error: String) {
                assertTrue(false)
                synchronized (syncObject) { syncObject.notify() }
            }
        })
        synchronized (syncObject) { syncObject.wait() }
    }

    @Test fun test04CreateTransaction() {
        val transactionBody = TransactionBody(4.2).apply {
            totalTransactionAmount = 9.23
            locationId = "there"
            clientTransactionId = null
            sample = false
        }
        val syncObject = Object()
        Bucket.createTransaction(transactionBody, callback = object : Callback.CreateTransaction {
            override fun onSuccess(createTransactionResponse: CreateTransactionResponse) {
                TestCache.customerCode = createTransactionResponse.customerCode.toString()
                assertTrue(4.2 == createTransactionResponse.amount)
                synchronized (syncObject) { syncObject.notify() }
            }

            override fun onError(error: String) {
                assertTrue(false)
                synchronized (syncObject) { syncObject.notify() }
            }
        })
        synchronized (syncObject) { syncObject.wait() }
    }

    @Test fun test05RefundTransaction() {
        val syncObject = Object()
        Bucket.refundTransaction(TestCache.customerCode, object : Callback.RefundTransaction {
            override fun onSuccess(message: String) {
                assertTrue(message == "Successfully refunded the transaction.")
                synchronized (syncObject) { syncObject.notify() }
            }
            override fun onError(error: String) {
                assertTrue(false)
                synchronized (syncObject) { syncObject.notify() }
            }
        })
        synchronized (syncObject) { syncObject.wait() }
    }

    @Test fun test6DeleteTransaction() {
        val syncObject = Object()
        Bucket.deleteTransaction(TestCache.customerCode, object : Callback.DeleteTransaction {
            override fun onSuccess(message: String) {
                assertTrue(message.isNotBlank())
                synchronized (syncObject) { syncObject.notify() }
            }

            override fun onError(error: String) {
                assertTrue(false)
                synchronized (syncObject) { syncObject.notify() }
            }
        })
        synchronized (syncObject) { syncObject.wait() }
    }

    @Test fun test7GetReport() {
        val getReportBody = ReportDateStringsBody(
                start = "2018-11-02 16:01:56+0800",
                end = "2018-11-29 16:01:56+0800")
        val syncObject = Object()
        Bucket.getReport(getReportBody, callback = object : Callback.GetReport {
            override fun onSuccess(getReportResponse: GetReportResponse) {
                assertTrue(getReportResponse.transactions.isNotEmpty())
                synchronized (syncObject) { syncObject.notify() }
            }

            override fun onError(error: String) {
                assertTrue(false)
                synchronized (syncObject) { syncObject.notify() }
            }
        })
        synchronized (syncObject) { syncObject.wait() }
    }

    @Test fun test8BucketAmount() {
        val bucketAmount = Bucket.bucketAmount(7.69)
        assertTrue(bucketAmount == 0.6900000000000004)
    }
}
