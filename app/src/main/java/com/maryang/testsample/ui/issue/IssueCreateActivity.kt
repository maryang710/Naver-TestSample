package com.maryang.testsample.ui.issue

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import com.maryang.testsample.R
import com.maryang.testsample.base.BaseActivity
import com.maryang.testsample.entity.GithubRepo
import com.maryang.testsample.util.extension.showToast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_issue_create.*
import java.util.concurrent.TimeUnit

class IssueCreateActivity : BaseActivity() {

    companion object {
        private const val KEY_REPO = "KEY_REPO"

        fun start(context: Context, repo: GithubRepo) {
            context.run {
                startActivity(
                    Intent(this, IssueCreateActivity::class.java)
                        .putExtra(KEY_REPO, repo)
                )
            }
        }
    }

    private val viewModel: IssueCreateViewModel by lazy {
        IssueCreateViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_create)
        supportActionBar?.run {
            title = "이슈 생성"
            setDisplayHomeAsUpEnabled(true)
        }
        viewModel.onCreate(intent.getParcelableExtra(KEY_REPO)!!)
        setEditText()
        setOnClickListener()
    }

    @SuppressLint("CheckResult")
    private fun setEditText() {
        Observable.merge(
            titleText.textChanges(), bodyText.textChanges()
        ).subscribe {
            enableComplete(it.isNotEmpty())
        }
    }

    private fun enableComplete(enable: Boolean) {
        complete.isEnabled = enable
        complete.setBackgroundColor(
            if (enable) getColor(R.color.colorPrimary)
            else getColor(R.color.grey_500)
        )
    }

    @SuppressLint("CheckResult")
    private fun setOnClickListener() {
        complete.clicks()
            .throttleFirst(1, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .flatMapSingle { viewModel.create(titleText.text.toString(), bodyText.text.toString()) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                showToast("이슈를 생성하였습니다")
                finish()
            }
    }
}
