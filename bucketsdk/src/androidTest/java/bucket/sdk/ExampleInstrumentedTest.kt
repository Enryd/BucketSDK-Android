package bucket.sdk

import android.os.Build
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import bucket.sdk.callbacks.*
import bucket.sdk.models.Error
import bucket.sdk.models.Transaction
import bucket.sdk.models.responses.ReportTransaction
import bucket.sdk.v2.Bucket
import bucket.sdk.v2.Callback
import bucket.sdk.v2.cache.Credentials
import bucket.sdk.v2.json.transaction.CreateTransactionResponse
import bucket.sdk.v2.json.transaction.TransactionBody
import bucket.sdk.v2.service.BucketRepositoryImpl
import bucket.sdk.v2.service.BucketService
import com.pawegio.kandroid.e
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var customerCode: String

    private fun setupTerminal() {
        Bucket.appContext = InstrumentationRegistry.getTargetContext()
        Credentials.retailerCode = "bckt-1"
        Credentials.country = "us"
    }

    @Test fun useAppContext() {
        Bucket.appContext = InstrumentationRegistry.getTargetContext()
        assert(bucket.sdk.v2.cache.Credentials.country != null)
    }

    @Test fun testRegisteringDevice() {
        Bucket.appContext = InstrumentationRegistry.getTargetContext()
        Bucket.registerTerminal("bckt-1", "us", object : Callback.RegisterTerminal {
            override fun onSuccess() {
                e("testRegisteringDevice - success")
                assert(true)
            }
            override fun onError(error: String) {
                e("testRegisteringDevice - failed")
                assert(false)
            }
        })
    }

    @Test fun testGetBillDenominations() {
        setupTerminal()
        Bucket.getBillDenominations(object : Callback.GetBillDenominations {
            override fun onSuccess() {
                assert(true)
            }

            override fun onError(error: String) {
                assert(false)
            }
        })
    }

    @Test fun testCreateTransaction() {
        setupTerminal()
        val transactionBody = TransactionBody(4.2).apply {
            totalTransactionAmount = 9.23
            locationId = "there"
            clientTransactionId = null
            sample = false
        }
        Bucket.createTransaction(transactionBody, callback = object : Callback.CreateTransaction {
            override fun onSuccess(createTransactionResponse: CreateTransactionResponse) {
                customerCode = createTransactionResponse.customerCode.toString()
                assertEquals(4.2, createTransactionResponse.amount)
            }

            override fun onError(error: String) {
                assertTrue(error, false)
            }
        })
    }

    @Test fun testDeleteTransaction() {
        Bucket.deleteTransaction(customerCode, object : Callback.DeleteTransaction {
            override fun onSuccess(message: String) {
                assertTrue(message.isNotBlank())
            }

            override fun onError(error: String) {
                assertTrue(error, false)
            }
        })
    }

    @Test fun bucketAmount() {

        val theAmountInt = 397
        val bucketAmount = Bucket.bucketAmount(7.69)

        assertTrue(bucketAmount == 0.6900000000000004)

    }
//    @Test fun useAppContext() {
//        // Context of the app under test.
//
//        val appContext = InstrumentationRegistry.getTargetContext()
//
//        Log.e("bucketAmount", "${Bucket.bucketAmount(10.53)}")
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
//    @Test fun bucketAmount() {
//
//        // Make sure the bucket amount function is working:
//        Bucket.appContext = InstrumentationRegistry.getTargetContext()
//
//        val theAmountInt = 397
//        val theAMountD = theAmountInt/100.0
//        val bucketAmount = Bucket.bucketAmount(7.69)
//
//        assertTrue(bucketAmount == 0.6900000000000004)
//
//    }

}
