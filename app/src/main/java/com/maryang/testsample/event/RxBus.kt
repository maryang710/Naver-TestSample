package com.maryang.testsample.event

import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.subjects.PublishSubject


object RxBus {
    private val bus = PublishSubject.create<Event>()

    fun post(event: Event) {
        bus.onNext(event)
    }

    fun observe() =
        bus.toFlowable(BackpressureStrategy.BUFFER)
}
