package bucket.sdk;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import bucket.sdk.v2.Bucket;
import bucket.sdk.v2.Callback;
import bucket.sdk.v2.Callback.CreateTransaction;
import bucket.sdk.v2.Callback.GetBillDenominations;
import bucket.sdk.v2.Callback.RegisterTerminal;
import bucket.sdk.v2.json.reporting.GetReportResponse;
import bucket.sdk.v2.json.reporting.ReportDateStringsBody;
import bucket.sdk.v2.json.transaction.CreateTransactionResponse;
import bucket.sdk.v2.json.transaction.TransactionBody;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

import org.jetbrains.annotations.NotNull;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RetailerInstrumentedJavaTest {
    @Test
    public void test1UseAppContext() {
        Bucket.setAppContext(InstrumentationRegistry.getTargetContext());
        assertTrue(true);
        TestCache.setCustomerCode("");
    }

    @Test
    public void test2RegisteringDevice() {
        Bucket.setAppContext(InstrumentationRegistry.getTargetContext());
        final Object syncObject = new Object();
        Bucket.registerTerminal("bckt-1", "us", new RegisterTerminal() {
            public void onSuccess() {
                assertTrue(true);
                synchronized (syncObject) { syncObject.notify(); }
            }

            public void onError(@NotNull String error) {
                Intrinsics.checkParameterIsNotNull(error, "error");
                fail();
                synchronized (syncObject) { syncObject.notify(); }
            }
        });
        synchronized (syncObject) {
            try { syncObject.wait(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    @Test
    public final void test3GetBillDenominations() {
        final Object syncObject = new Object();
        Bucket.getBillDenominations(new GetBillDenominations() {
            public void onSuccess() {
                assertTrue(true);
                synchronized (syncObject) { syncObject.notify(); }
            }

            public void onError(@NotNull String error) {
                Intrinsics.checkParameterIsNotNull(error, "error");
                fail();
                synchronized (syncObject) { syncObject.notify(); }
            }
        });
        synchronized (syncObject) {
            try { syncObject.wait(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    @Test
    public final void test4CreateTransaction() {
        TransactionBody transactionBody = new TransactionBody(4.2D, null, null, null, null);
        transactionBody.setTotalTransactionAmount(9.23D);
        transactionBody.setLocationId("there");
        transactionBody.setClientTransactionId(null);
        transactionBody.setSample(false);
        final Object syncObject = new Object();
        Bucket.createTransaction(transactionBody, null, null, new CreateTransaction() {
            public void onSuccess(@NotNull CreateTransactionResponse createTransactionResponse) {
                Intrinsics.checkParameterIsNotNull(createTransactionResponse, "createTransactionResponse");
                TestCache.setCustomerCode(String.valueOf(createTransactionResponse.getCustomerCode()));
                assertTrue(Intrinsics.areEqual(4.2D, createTransactionResponse.getAmount()));
                synchronized (syncObject) { syncObject.notify(); }
            }

            public void onError(@NotNull String error) {
                Intrinsics.checkParameterIsNotNull(error, "error");
                fail();
                synchronized (syncObject) { syncObject.notify(); }
            }
        });

        synchronized (syncObject) {
            try { syncObject.wait(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    @Test
    public final void test5RefundTransaction() {
        final Object syncObject = new Object();
        Bucket.refundTransaction(TestCache.getCustomerCode(), new Callback.RefundTransaction() {
            public void onSuccess(@NotNull String message) {
                Intrinsics.checkParameterIsNotNull(message, "message");
                assertTrue(Intrinsics.areEqual(message, "Successfully refunded the transaction."));
                synchronized(syncObject) { syncObject.notify(); }
            }

            public void onError(@NotNull String error) {
                Intrinsics.checkParameterIsNotNull(error, "error");
                fail();
                synchronized(syncObject) { syncObject.notify(); }
            }
        });
        synchronized(syncObject) {
            try { syncObject.wait(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    @Test
    public final void test6DeleteTransaction() {
        final Object syncObject = new Object();
        Bucket.Companion.deleteTransaction(TestCache.getCustomerCode(), new Callback.DeleteTransaction() {
            public void onSuccess(@NotNull String message) {
                Intrinsics.checkParameterIsNotNull(message, "message");
                assertTrue(!StringsKt.isBlank(message));
                synchronized(syncObject) { syncObject.notify(); }
            }

            public void onError(@NotNull String error) {
                Intrinsics.checkParameterIsNotNull(error, "error");
                fail();
                synchronized(syncObject) { syncObject.notify(); }
            }
        });
        synchronized(syncObject) {
            try { syncObject.wait(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    @Test
    public final void test7GetReport() {
        ReportDateStringsBody getReportBody = new ReportDateStringsBody("2018-11-02 16:01:56+0800", "2018-11-29 16:01:56+0800", null, null, null);
        final Object syncObject = new Object();
        Bucket.getReport(getReportBody, null, null, null, new Callback.GetReport() {
            public void onSuccess(@NotNull GetReportResponse getReportResponse) {
                Intrinsics.checkParameterIsNotNull(getReportResponse, "getReportResponse");
                assertTrue(!getReportResponse.getTransactions().isEmpty());
                synchronized(syncObject) { syncObject.notify(); }
            }

            public void onError(@NotNull String error) {
                Intrinsics.checkParameterIsNotNull(error, "error");
                fail();
                synchronized(syncObject) { syncObject.notify(); }
            }
        });
    }

    @Test
    public final void test8BucketAmount() {
        double bucketAmount = Bucket.Companion.bucketAmount(7.69D);
        assertEquals(0.6900000000000004D, bucketAmount, 0.0);
    }
}
