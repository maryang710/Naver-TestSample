package com.maryang.testsample.ui.repos

import com.maryang.testsample.base.BaseViewModel
import com.maryang.testsample.data.repository.GithubRepository
import com.maryang.testsample.entity.GithubRepo
import com.maryang.testsample.util.provider.SchedulerProvider
import com.maryang.testsample.util.provider.SchedulerProviderInterface
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class GithubReposViewModel(
    private val repository: GithubRepository = GithubRepository(),
    private val schedulerProvider: SchedulerProviderInterface = SchedulerProvider()
) : BaseViewModel() {

    private val searchSubject = BehaviorSubject.createDefault("" to false)
    val loadingState = PublishSubject.create<Boolean>()
    val reposState = PublishSubject.create<List<GithubRepo>>()

    fun onCreate() {
        compositeDisposable += searchSubject
            .debounce(400, TimeUnit.MILLISECONDS, schedulerProvider.computation())
            .distinctUntilChanged()
            .observeOn(schedulerProvider.main())
            .doOnNext { loadingState.onNext(it.second) }
            .observeOn(schedulerProvider.io())
            .switchMapSingle { (searchText, loading) ->
                if (searchText.isEmpty()) Single.just(emptyList())
                else repository.searchGithubRepos(searchText)
            }
            .switchMapSingle { repos ->
                Completable.merge(
                    repos.map { repo ->
                        repository.checkStar(repo.owner.userName, repo.name)
                            .doOnComplete { repo.star = true }
                            .onErrorComplete()
                    }
                ).toSingleDefault(repos)
            }
            .observeOn(schedulerProvider.main())
            .doOnNext { loadingState.onNext(false) }
            .subscribe(reposState::onNext)
    }

    fun searchGithubRepos(search: String) {
        searchSubject.onNext(search to true)
    }

    fun searchGithubRepos() {
        searchSubject.onNext(searchSubject.value!!.first to false)
    }
}
