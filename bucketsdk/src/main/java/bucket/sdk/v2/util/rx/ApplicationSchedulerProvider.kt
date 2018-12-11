package bucket.sdk.v2.util.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Scheduler provider
 */
internal class ApplicationSchedulerProvider : SchedulerProvider {

    override fun io() = Schedulers.io()

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()

    override fun computation() = Schedulers.computation()

}