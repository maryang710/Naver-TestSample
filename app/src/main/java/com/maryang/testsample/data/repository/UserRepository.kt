package com.maryang.testsample.data.repository

import io.reactivex.rxjava3.core.Completable


class UserRepository {

    fun signup(): Completable =
        Completable.complete()
}
