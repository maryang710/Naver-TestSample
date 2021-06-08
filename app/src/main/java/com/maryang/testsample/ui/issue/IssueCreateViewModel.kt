package com.maryang.testsample.ui.issue

import com.maryang.testsample.base.BaseViewModel
import com.maryang.testsample.data.repository.GithubRepository
import com.maryang.testsample.entity.GithubRepo

class IssueCreateViewModel(
    private val repository: GithubRepository = GithubRepository()
) : BaseViewModel() {

    lateinit var repo: GithubRepo

    fun onCreate(repo: GithubRepo) {
        this.repo = repo
    }

    fun create(title: String, body: String) =
        repository.createIssue(repo.owner.userName, repo.name, title, body)
}
