package com.maryang.testsample.observer

import com.maryang.testsample.util.ErrorHandler
import io.reactivex.rxjava3.observers.DisposableSingleObserver


abstract class DefaultSingleObserver<T> : DisposableSingleObserver<T>() {

    override fun onError(e: Throwable) {
        ErrorHandler.handle(e)
    }
}
