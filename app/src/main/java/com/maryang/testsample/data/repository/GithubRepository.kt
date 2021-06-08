package com.maryang.testsample.data.repository

import com.google.gson.reflect.TypeToken
import com.maryang.testsample.data.db.AppDatabase
import com.maryang.testsample.data.request.CreateIssueRequest
import com.maryang.testsample.data.source.ApiManager
import com.maryang.testsample.entity.GithubRepo
import com.maryang.testsample.entity.Issue
import com.maryang.testsample.entity.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers


class GithubRepository {

    private val api = ApiManager.githubApi
    private val dao = AppDatabase.get().githubDao()

    fun save(repo: GithubRepo) =
        dao.insert(repo)
            .subscribeOn(Schedulers.io())

    fun searchGithubRepos(q: String): Single<List<GithubRepo>> =
        api.searchRepos(q)
            .map {
                it.asJsonObject.getAsJsonArray("items")
            }
            .map {
                val type = object : TypeToken<List<GithubRepo>>() {}.type
                ApiManager.gson.fromJson(it, type) as List<GithubRepo>
            }
            .subscribeOn(Schedulers.io())

    fun checkStar(owner: String, repo: String): Completable =
        api.checkStar(owner, repo)
            .subscribeOn(Schedulers.io())

    fun star(owner: String, repo: String): Completable =
        api.star(owner, repo)
            .subscribeOn(Schedulers.io())

    fun unstar(owner: String, repo: String): Completable =
        api.unstar(owner, repo)
            .subscribeOn(Schedulers.io())

    fun issues(owner: String, repo: String): Single<List<Issue>> =
        api.issues(owner, repo)
            .subscribeOn(Schedulers.io())

    fun createIssue(owner: String, repo: String, title: String, body: String): Single<Issue> =
        api.createIssue(owner, repo, CreateIssueRequest(title, body))
            .subscribeOn(Schedulers.io())

    fun getFollowers(url: String): Single<List<User>> =
        api.getFollowers(url)
            .subscribeOn(Schedulers.io())

    fun getRepos(url: String): Single<List<GithubRepo>> =
        api.getRepos(url)
            .subscribeOn(Schedulers.io())
}
