package bucket.sdk

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import bucket.sdk.v2.Bucket
import bucket.sdk.v2.Callback
import bucket.sdk.v2.json.reporting.GetReportResponse
import bucket.sdk.v2.json.reporting.ReportDateStringsBody
import bucket.sdk.v2.json.transaction.CreateTransactionResponse
import bucket.sdk.v2.json.transaction.TransactionBody
import com.pawegio.kandroid.e
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
class ExampleInstrumentedTest {

    private var customerCode: String = ""

    @Test fun test1UseAppContext() {
        Bucket.appContext = InstrumentationRegistry.getTargetContext()
        assert(bucket.sdk.v2.cache.Credentials.country != null)
    }

    @Test fun test2RegisteringDevice() {
        val syncObject = Object()
        Bucket.registerTerminal("bckt-1", "us", object : Callback.RegisterTerminal {
            override fun onSuccess() {
                e("testRegisteringDevice - success")
                assert(true)
                synchronized (syncObject) { syncObject.notify() }
            }
            override fun onError(error: String) {
                e("testRegisteringDevice - failed")
                assert(false)
                synchronized (syncObject) { syncObject.notify() }
            }
        })
        synchronized (syncObject) { syncObject.wait() }
    }

    @Test fun test3GetBillDenominations() {
        val syncObject = Object()
        Bucket.getBillDenominations(object : Callback.GetBillDenominations {
            override fun onSuccess() {
                assert(true)
                synchronized (syncObject) { syncObject.notify() }
            }

            override fun onError(error: String) {
                assert(false)
                synchronized (syncObject) { syncObject.notify() }
            }
        })
        synchronized (syncObject) {
            syncObject.wait()
        }
    }

    @Test fun test4CreateTransaction() {
        Log.e("customerCode1", customerCode)
        val transactionBody = TransactionBody(4.2).apply {
            totalTransactionAmount = 9.23
            locationId = "there"
            clientTransactionId = null
            sample = false
        }
        val syncObject = Object()
        Bucket.createTransaction(transactionBody, callback = object : Callback.CreateTransaction {
            override fun onSuccess(createTransactionResponse: CreateTransactionResponse) {
                customerCode = createTransactionResponse.customerCode.toString()
                assert(4.2 == createTransactionResponse.amount)
                synchronized (syncObject) { syncObject.notify() }
            }

            override fun onError(error: String) {
                assert(false)
                synchronized (syncObject) { syncObject.notify() }
            }
        })
        synchronized (syncObject) { syncObject.wait() }
    }

    @Test fun test5RefundTransaction() {
        val syncObject = Object()
        Bucket.refundTransaction(customerCode, object : Callback.RefundTransaction {
            override fun onSuccess(message: String) {
                assert(message.isNotBlank())
                synchronized (syncObject) { syncObject.notify() }
            }
            override fun onError(error: String) {
                assert(false)
                synchronized (syncObject) { syncObject.notify() }
            }
        })
        synchronized (syncObject) { syncObject.wait() }
    }

    @Test fun test6DeleteTransaction() {
        val syncObject = Object()
        Bucket.deleteTransaction(customerCode, object : Callback.DeleteTransaction {
            override fun onSuccess(message: String) {
                assert(message.isNotBlank())
                synchronized (syncObject) { syncObject.notify() }
            }

            override fun onError(error: String) {
                assert(false)
                synchronized (syncObject) { syncObject.notify() }
            }
        })
        synchronized (syncObject) { syncObject.wait() }
    }

    @Test fun test7GetReport() {
        val getReportBody = ReportDateStringsBody(
                start = "2018-11-02 16:01:56+0800",
                end = "2018-11-29 16:01:56+0800"
        )
        val syncObject = Object()
        Bucket.getReport(getReportBody, callback = object : Callback.GetReport {
            override fun onSuccess(getReportResponse: GetReportResponse) {
                assert(getReportResponse.bucketSales != null)
                synchronized (syncObject) { syncObject.notify() }
            }

            override fun onError(error: String) {
                assert(false)
                synchronized (syncObject) { syncObject.notify() }
            }
        })
        synchronized (syncObject) { syncObject.wait() }
    }

    @Test fun test8BucketAmount() {
        val bucketAmount = Bucket.bucketAmount(7.69)
        assert(bucketAmount == 0.6900000000000004)
    }
//    @Test fun testUseAppContext() {
//        // Context of the app under test.
//
//        val appContext = InstrumentationRegistry.getTargetContext()
//
//        Log.e("testBucketAmount", "${Bucket.testBucketAmount(10.53)}")
//
//
//        assertEquals("bucket.sdk.test", appContext.packageName)
//    }
//
//    @Test fun testRegisteringDevice() {
//
//        Bucket.appContext = InstrumentationRegistry.getTargetContext()
//        Bucket.environment = DeploymentEnvironment.Development
//        Credentials.setName("asd")
//        Log.e("name", "${Credentials.name()}")
//        Credentials.setRetailerCode("BCKT-1")
//        // Get the client id & client secret for this retailer:
//        Bucket.registerTerminal("US", object : RegisterTerminal {
//            override fun success(isApproved: Boolean) {
//                assert(true)
//            }
//            override fun didError(error: Error) {
//                assertTrue(error.message, false)
//            }
//        })
//        Thread.sleep(5000)
//        Bucket.reporting("2018-11-01 16:07:51+0800", "2018-11-21 16:07:51+0800", "", Build.SERIAL, callback = object : Reporting{
//            override fun success(bucketTotal: Double?, transactionsList: List<ReportTransaction>) {
//            }
//
//            override fun didError(error: Error) {
//            }
//
//        })
//        Thread.sleep(5000)
//    }
//
//    @Test fun testCreateTransaction() {
//
//        Bucket.appContext = InstrumentationRegistry.getTargetContext()
//        Bucket.environment = DeploymentEnvironment.Development
//        Credentials.apply {
//            setRetailerCode("BCKT-1")
//            setTerminalSecret("abc")
//            setCountryCode("US")
//        }
//
//        val transaction = Transaction(0.54, 7.89)
//        transaction.create(callback = object : CreateTransaction {
//            override fun transactionCreated() {
//                assert(true)
//            }
//            override fun didError(error: Error) {
//                assertTrue(error.message ?: "", false)
//            }
//        })
//        Thread.sleep(5000)
//    }
//
//    @Test fun testDeleteTransaction() {
//
//        Bucket.appContext = InstrumentationRegistry.getTargetContext()
//
//        val transaction = Transaction(0.54, 7.89)
//        transaction.customerCode = "us.eDZ9LBdvununS"
//
//        transaction.delete(object : DeleteTransaction {
//            override fun transactionDeleted() {
//                assert(true)
//            }
//            override fun didError(error: Error) {
//                assertTrue(error.message ?: "", false)
//            }
//        })
//
//        Thread.sleep(5000)
//    }
//
//    @Test fun fetchBillDenoms() {
//
//        Bucket.appContext = InstrumentationRegistry.getTargetContext()
//
//        Bucket.fetchBillDenominations(object : BillDenomination {
//            override fun setBillDenoms() {
//                assertTrue(true)
//            }
//            override fun didError(error: Error) {
//                assertTrue(error.message ?: "", false)
//            }
//        })
//
//        Thread.sleep(5000)
//    }
//
//    @Test fun testBucketAmount() {
//
//        // Make sure the bucket amount function is working:
//        Bucket.appContext = InstrumentationRegistry.getTargetContext()
//
//        val theAmountInt = 397
//        val theAMountD = theAmountInt/100.0
//        val testBucketAmount = Bucket.testBucketAmount(7.69)
//
//        assertTrue(testBucketAmount == 0.6900000000000004)
//
//    }

}
