package com.maryang.testsample.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding4.widget.checkedChanges
import com.maryang.testsample.R
import com.maryang.testsample.base.BaseViewModelActivity
import com.maryang.testsample.ui.repos.GithubReposActivity
import io.reactivex.rxjava3.kotlin.plusAssign
import kotlinx.android.synthetic.main.activity_signup.*


class SignupActivity : BaseViewModelActivity() {

    override val viewModel: SignupViewModel by lazy {
        SignupViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        compositeDisposable += viewModel.loadingState.subscribe {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        compositeDisposable += viewModel.buttonState.subscribe {
            signupButton.isEnabled = it
            signupButton.setBackgroundColor(
                if (it) getColor(R.color.colorPrimary)
                else getColor(R.color.grey_500)
            )
        }

        compositeDisposable += viewModel.state.subscribe {
            when (it) {
                SignupViewModel.State.SIGNUP_SUCCESS ->
                    startGithubRepos()
            }
        }

        setOnClickListener()
    }

    private fun setOnClickListener() {
        checkbox1.checkedChanges().subscribe(viewModel.check1State)
        checkbox2.checkedChanges().subscribe(viewModel.check2State)
        checkbox3.checkedChanges().subscribe(viewModel.check3State)
        signupButton.setOnClickListener {
            viewModel.signup()
        }
    }

    private fun startGithubRepos() {
        startActivity(
            Intent(this, GithubReposActivity::class.java)
        )
        finish()
    }
}
