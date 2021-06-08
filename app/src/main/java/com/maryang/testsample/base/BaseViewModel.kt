package com.maryang.testsample.base

import io.reactivex.rxjava3.disposables.CompositeDisposable


abstract class BaseViewModel {

    protected val compositeDisposable = CompositeDisposable()

    fun onDestroy() {
        compositeDisposable.dispose()
    }
}
