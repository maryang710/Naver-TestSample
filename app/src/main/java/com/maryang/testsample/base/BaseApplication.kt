package com.maryang.testsample.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.maryang.testsample.event.LogoutEvent
import com.maryang.testsample.event.RxBus
import com.maryang.testsample.util.ErrorHandler
import com.maryang.testsample.util.extension.showToast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.plugins.RxJavaPlugins

class BaseApplication : Application() {

    companion object {
        lateinit var appContext: Context
        const val TAG = "TestSample"
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        Stetho.initializeWithDefaults(this)
        setErrorHanlder()
    }

    @SuppressLint("CheckResult")
    private fun setErrorHanlder() {
        // onError 가 없거나, onError에서 또 Exception이 나면 오는애
        RxJavaPlugins.setErrorHandler {
            ErrorHandler.handle(it)
        }

        RxBus.observe()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it) {
                    is LogoutEvent ->
                        showToast("Logout")
                }
            }
    }
}
