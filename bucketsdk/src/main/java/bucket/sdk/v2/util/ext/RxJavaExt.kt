package bucket.sdk.v2.util.ext

import bucket.sdk.v2.util.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Use SchedulerProvider configuration for Observable
 */
internal fun Completable.with(schedulerProvider: SchedulerProvider): Completable
        = this.observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.io())

/**
 * Use SchedulerProvider configuration for Single
 */
internal fun <T> Single<T>.with(schedulerProvider: SchedulerProvider): Single<T>
        = this.observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.io())