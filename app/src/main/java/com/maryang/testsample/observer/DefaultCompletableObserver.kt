package com.maryang.testsample.observer

import com.maryang.testsample.util.ErrorHandler
import io.reactivex.rxjava3.observers.DisposableCompletableObserver


abstract class DefaultCompletableObserver : DisposableCompletableObserver() {

    override fun onError(e: Throwable) {
        ErrorHandler.handle(e)
    }
}
