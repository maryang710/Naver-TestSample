package com.maryang.testsample.util.provider

import io.reactivex.rxjava3.core.Scheduler

interface SchedulerProviderInterface {
    fun io(): Scheduler
    fun computation(): Scheduler
    fun main(): Scheduler
}
