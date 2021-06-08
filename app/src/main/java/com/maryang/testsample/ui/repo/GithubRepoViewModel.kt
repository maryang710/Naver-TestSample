package com.maryang.testsample.ui.repo

import com.maryang.testsample.base.BaseViewModel
import com.maryang.testsample.data.repository.GithubRepository
import com.maryang.testsample.entity.GithubRepo
import com.maryang.testsample.entity.Issue
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.subjects.BehaviorSubject


class GithubRepoViewModel(
    private val repository: GithubRepository = GithubRepository()
) : BaseViewModel() {

    val repoState = BehaviorSubject.create<GithubRepo>()
    val issuesState = BehaviorSubject.createDefault<List<Issue>>(emptyList())

    fun onCreate(repo: GithubRepo) {
        repoState.onNext(repo)
        compositeDisposable += repository.issues(repo.owner.userName, repo.name)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(issuesState::onNext)
    }

    fun save(repo: GithubRepo) =
        repository.save(repo).subscribe()

    fun onClickStar() {
        val repo = repoState.value!!
        val doStar = !repo.star
        repoState.onNext(
            repo.copy(
                star = !repo.star,
                stargazersCount =
                if (doStar) repo.stargazersCount + 1
                else repo.stargazersCount - 1
            )
        )
        compositeDisposable +=
            (if (doStar)
                repository.star(repo.owner.userName, repo.name)
            else
                repository.unstar(repo.owner.userName, repo.name))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ }, {
                    repoState.onNext(repo)
                })
    }
}
