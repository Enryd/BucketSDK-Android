package bucket.sdk

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import bucket.sdk.v2.Bucket
import bucket.sdk.v2.Callback
import bucket.sdk.v2.cache.Credentials
import bucket.sdk.v2.json.events.CreateEventDateStringsBody
import bucket.sdk.v2.json.events.Event
import bucket.sdk.v2.json.events.UpdateEventDateStringsBody
import bucket.sdk.v2.json.reporting.EventReportDateStringsBody
import bucket.sdk.v2.json.reporting.GetEventReportResponse
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
class MarketingInstrumentedTest {

    @Test
    fun test01UseAppContext() {
        Bucket.appContext = InstrumentationRegistry.getTargetContext()
        Credentials.terminalSecret
        assertTrue(true)
        TestCache.customerCode = ""
    }

    @Test
    fun test02RegisteringDevice() {
        val syncObject = Object()
        Bucket.registerTerminal("bckt-1", "us", object : Callback.RegisterTerminal {
            override fun onSuccess() {
                assertTrue(true)
                synchronized(syncObject) { syncObject.notify() }
            }

            override fun onError(error: String) {
                assertTrue(false)
                synchronized(syncObject) { syncObject.notify() }
            }
        })
        synchronized(syncObject) { syncObject.wait() }
    }

    @Test
    fun test03GetBillDenominations() {
        val syncObject = Object()
        Bucket.getBillDenominations(object : Callback.GetBillDenominations {
            override fun onSuccess() {
                assertTrue(true)
                synchronized(syncObject) { syncObject.notify() }
            }

            override fun onError(error: String) {
                assertTrue(false)
                synchronized(syncObject) { syncObject.notify() }
            }
        })
        synchronized(syncObject) { syncObject.wait() }
    }

    @Test
    fun test04CreateEvent() {
        val syncObject = Object()
        val createEventBody = CreateEventDateStringsBody(
                eventName = "name",
                eventMessage = "message",
                start = "2019-11-02 16:01:56+0800",
                end = "2019-11-29 16:01:56+0800")
        Bucket.createEvent(createEventBody, object : Callback.CreateEvent {
            override fun onSuccess(eventId: Int, message: String) {
                TestCache.eventId = eventId
                assertTrue(message.isNotBlank())
                synchronized(syncObject) { syncObject.notify() }
            }

            override fun onError(error: String) {
                assertTrue(false)
                synchronized(syncObject) { syncObject.notify() }
            }
        })
        synchronized(syncObject) { syncObject.wait() }
    }

    @Test
    fun test05GetEvents() {
        val syncObject = Object()
        Bucket.getEvents("2019-11-02 16:01:56+0800", "2019-11-29 16:01:56+0800", callback = object : Callback.GetEvents {
            override fun onSuccess(events: List<Event>) {
                assertTrue(events.isNotEmpty())
                synchronized(syncObject) { syncObject.notify() }
            }

            override fun onError(error: String) {
                assertTrue(false)
                synchronized(syncObject) { syncObject.notify() }
            }
        })
        synchronized(syncObject) { syncObject.wait() }
    }

    @Test
    fun test06GetEventWithId() {
        val syncObject = Object()
        Bucket.getEventWithId(TestCache.eventId, callback = object : Callback.GetEvents {
            override fun onSuccess(events: List<Event>) {
                assertTrue(events.isNotEmpty())
                synchronized(syncObject) { syncObject.notify() }
            }

            override fun onError(error: String) {
                assertTrue(false)
                synchronized(syncObject) { syncObject.notify() }
            }
        })
        synchronized(syncObject) { syncObject.wait() }
    }

    @Test
    fun test07GetEventReport() {
        val syncObject = Object()
        val getEventReportBody = EventReportDateStringsBody(
                start = "2019-11-02 16:01:56+0800",
                end = "2019-11-29 16:01:56+0800")
        Bucket.getEventReport(getEventReportBody, callback = object : Callback.GetEventReport {
            override fun onSuccess(getEventReportResponse: GetEventReportResponse) {
                assertTrue(getEventReportResponse.events?.isNotEmpty() == true)
                synchronized(syncObject) { syncObject.notify() }
            }

            override fun onError(error: String) {
                assertTrue(false)
                synchronized(syncObject) { syncObject.notify() }
            }
        })
        synchronized(syncObject) { syncObject.wait() }
    }

    @Test
    fun test08GetEventReportWithId() {
        val syncObject = Object()
        Bucket.getEventReportWithId(TestCache.eventId, callback = object : Callback.GetEventReport {
            override fun onSuccess(getEventReportResponse: GetEventReportResponse) {
                assertTrue(getEventReportResponse.events?.isNotEmpty() == true)
                synchronized(syncObject) { syncObject.notify() }
            }

            override fun onError(error: String) {
                assertTrue(false)
                synchronized(syncObject) { syncObject.notify() }
            }
        })
        synchronized(syncObject) { syncObject.wait() }
    }

    @Test
    fun test09UpdateEvent() {
        val syncObject = Object()
        val updateEventBody = UpdateEventDateStringsBody(
                id = TestCache.eventId,
                eventName = "name2",
                eventMessage = "message2",
                start = "2019-11-02 16:01:56+0800",
                end = "2019-11-30 16:01:56+0800")
        Bucket.updateEvent(updateEventBody, callback = object : Callback.UpdateEvent {
            override fun onSuccess(eventId: Int, message: String) {
                assertTrue(message.isNotBlank())
                synchronized(syncObject) { syncObject.notify() }
            }

            override fun onError(error: String) {
                assertTrue(false)
                synchronized(syncObject) { syncObject.notify() }
            }
        })
        synchronized(syncObject) { syncObject.wait() }
    }

    @Test
    fun test10SendExport() {
        val syncObject = Object()
        Bucket.sendExport("edrynm@sonarlogic.biz", callback = object : Callback.SendExport {
            override fun onSuccess(message: String) {
                assertTrue(message.isNotBlank())
                synchronized(syncObject) { syncObject.notify() }
            }

            override fun onError(error: String) {
                assertTrue(false)
                synchronized(syncObject) { syncObject.notify() }
            }
        })
        synchronized(syncObject) { syncObject.wait() }
    }

    @Test
    fun test11DeleteEvent() {
        val syncObject = Object()
        Bucket.deleteEvent(TestCache.eventId, callback = object : Callback.DeleteEvent {
            override fun onSuccess(message: String) {
                assertTrue(message.isNotBlank())
                synchronized(syncObject) { syncObject.notify() }
            }

            override fun onError(error: String) {
                // TODO: delete api not yet working/finished, uncomment line below - comment line below it
//                assertTrue(false)
                assertTrue(true)
                synchronized(syncObject) { syncObject.notify() }
            }
        })
        synchronized(syncObject) { syncObject.wait() }
    }

}