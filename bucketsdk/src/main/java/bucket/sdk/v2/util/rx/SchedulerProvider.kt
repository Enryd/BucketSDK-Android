package bucket.sdk.v2.util.rx

import io.reactivex.Scheduler

/**
 * Rx Scheduler Provider
 */
internal interface SchedulerProvider {
    fun io(): Scheduler
    fun ui(): Scheduler
    fun computation(): Scheduler
}