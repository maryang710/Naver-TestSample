package com.maryang.testsample.util.provider

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SchedulerProvider : SchedulerProviderInterface {
    override fun io() =
        Schedulers.io()

    override fun computation() =
        Schedulers.computation()

    override fun main() =
        AndroidSchedulers.mainThread()
}
