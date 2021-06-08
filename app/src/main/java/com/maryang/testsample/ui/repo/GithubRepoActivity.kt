package com.maryang.testsample.ui.repo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.maryang.testsample.R
import com.maryang.testsample.base.BaseActivity
import com.maryang.testsample.entity.GithubRepo
import com.maryang.testsample.ui.issue.IssueCreateActivity
import com.maryang.testsample.ui.user.UserActivity
import io.reactivex.rxjava3.kotlin.plusAssign
import kotlinx.android.synthetic.main.activity_github_repo.*


class GithubRepoActivity : BaseActivity() {

    companion object {
        private const val KEY_REPO = "KEY_REPO"

        fun start(context: Context, repo: GithubRepo) {
            context.run {
                startActivity(
                    Intent(this, GithubRepoActivity::class.java)
                        .putExtra(KEY_REPO, repo)
                )
            }
        }
    }

    private val viewModel: GithubRepoViewModel by lazy {
        GithubRepoViewModel()
    }
    private val issuesAdapter: IssuesAdapter by lazy {
        IssuesAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_repo)

        issues.layoutManager = LinearLayoutManager(this)
        issues.adapter = issuesAdapter

        compositeDisposable += viewModel.repoState.subscribe {
            showRepo(it)
        }
        compositeDisposable += viewModel.issuesState.subscribe {
            issueLabel.visibility = View.VISIBLE
            issuesAdapter.items = it.toMutableList()
        }

        intent.getParcelableExtra<GithubRepo>(KEY_REPO)?.let {
            supportActionBar?.run {
                title = it.name
                setDisplayHomeAsUpEnabled(true)
            }
            viewModel.onCreate(it)
        }
        setOnClickListener()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_repo, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.create_issue -> {
                IssueCreateActivity.start(this, viewModel.repoState.value!!)
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        }
    }

    private fun showRepo(repo: GithubRepo) {
        Glide.with(this)
            .load(repo.owner.avatarUrl)
            .into(ownerImage)
        ownerName.text = repo.owner.userName
        starCount.text = repo.stargazersCount.toString()
        watcherCount.text = repo.watchersCount.toString()
        forksCount.text = repo.forksCount.toString()
        showStar(repo.star)
        viewModel.save(repo)
    }

    private fun setOnClickListener() {
        star.setOnClickListener { viewModel.onClickStar() }
        ownerImage.setOnClickListener { clickOwner() }
        ownerName.setOnClickListener { clickOwner() }
    }

    private fun clickOwner() {
        UserActivity.start(this, viewModel.repoState.value!!.owner)
    }

    private fun showStar(show: Boolean) {
        star.setImageResource(
            if (show) R.drawable.baseline_star_24 else R.drawable.baseline_star_border_24
        )
    }
}
