package com.maryang.testsample.util.provider

import io.reactivex.rxjava3.schedulers.TestScheduler

class TestSchedulerProvider : SchedulerProviderInterface {

    val testScheduler = TestScheduler()

    override fun io() =
        testScheduler

    override fun computation() =
        testScheduler

    override fun main() =
        testScheduler
}
