package com.maryang.testsample.ui.repos

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.maryang.testsample.base.BaseViewModelActivity
import io.reactivex.rxjava3.kotlin.plusAssign
import kotlinx.android.synthetic.main.activity_github_repos.*


class GithubReposActivity : BaseViewModelActivity() {

    override val viewModel: GithubReposViewModel by lazy {
        GithubReposViewModel()
    }
    private val adapter: GithubReposAdapter by lazy {
        GithubReposAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.maryang.testsample.R.layout.activity_github_repos)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = this.adapter
        refreshLayout.setOnRefreshListener {
            viewModel.searchGithubRepos()
        }

        searchText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(text: Editable?) {
                viewModel.searchGithubRepos(text.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        compositeDisposable += viewModel.loadingState.subscribe {
            if (it) showLoading() else hideLoading()
        }
        compositeDisposable += viewModel.reposState.subscribe {
            adapter.items = it
        }
        viewModel.onCreate()
    }

    private fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loading.visibility = View.GONE
        refreshLayout.isRefreshing = false
    }
}
