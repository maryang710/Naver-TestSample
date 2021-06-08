package com.maryang.testsample.ui.signup

import com.maryang.testsample.base.BaseViewModel
import com.maryang.testsample.data.repository.UserRepository
import com.maryang.testsample.observer.DefaultCompletableObserver
import com.maryang.testsample.util.provider.SchedulerProvider
import com.maryang.testsample.util.provider.SchedulerProviderInterface
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Function3
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.subjects.BehaviorSubject


class SignupViewModel(
    private val userRepository: UserRepository = UserRepository(),
    private val schedulerProvider: SchedulerProviderInterface = SchedulerProvider()
) : BaseViewModel() {

    val check1State = BehaviorSubject.createDefault(false)
    val check2State = BehaviorSubject.createDefault(false)
    val check3State = BehaviorSubject.createDefault(false)
    val buttonState = BehaviorSubject.createDefault(false)

    val loadingState = BehaviorSubject.createDefault(false)
    val state = BehaviorSubject.createDefault(State.NORMAL)

    init {
        compositeDisposable += Observable.combineLatest(
            check1State,
            check2State,
            check3State,
            Function3<Boolean, Boolean, Boolean, Boolean> { check1, check2, check3 ->
                check1 && check2 && check3
            }
        ).subscribe(buttonState::onNext)
    }

    fun signup() {
        loadingState.onNext(true)
        userRepository.signup()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.main())
            .subscribe(object : DefaultCompletableObserver() {
                override fun onComplete() {
                    loadingState.onNext(false)
                    state.onNext(State.SIGNUP_SUCCESS)
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    loadingState.onNext(false)
                }
            })
    }

    enum class State {
        NORMAL, SIGNUP_SUCCESS;
    }
}
