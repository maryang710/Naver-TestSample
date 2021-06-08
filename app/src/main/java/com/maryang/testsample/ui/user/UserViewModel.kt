package com.maryang.testsample.ui.user

import com.maryang.testsample.base.BaseViewModel
import com.maryang.testsample.data.repository.GithubRepository
import com.maryang.testsample.entity.GithubRepo
import com.maryang.testsample.entity.User
import com.maryang.testsample.observer.DefaultSingleObserver
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

class UserViewModel(
    private val repository: GithubRepository = GithubRepository()
) : BaseViewModel() {

    val userState = PublishSubject.create<User>()
    val followersCountState = BehaviorSubject.createDefault(0)
    val reposCountState = BehaviorSubject.createDefault(0)

    fun onCreate(user: User) {
        userState.onNext(user)
        getUserCounts(user)
    }

    private fun getUserCounts(user: User) {
        repository.getFollowers(user.followersUrl)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DefaultSingleObserver<List<User>>() {
                override fun onSuccess(t: List<User>) {
                    followersCountState.onNext(t.size)
                }
            })

        repository.getRepos(user.reposUrl)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DefaultSingleObserver<List<GithubRepo>>() {
                override fun onSuccess(t: List<GithubRepo>) {
                    reposCountState.onNext(t.size)
                }
            })
    }
}
