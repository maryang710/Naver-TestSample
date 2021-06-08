package com.maryang.testsample.util

import com.maryang.testsample.event.LogoutEvent
import com.maryang.testsample.event.RxBus
import retrofit2.HttpException


object ErrorHandler {

    fun handle(t: Throwable) {
        t.printStackTrace()
        if (t is HttpException) {
            // 401 익셉션은 토큰이 만료된 애라고 약속을 했다
            if (t.code() == 401) {
                RxBus.post(LogoutEvent())
            }
        }
    }
}
