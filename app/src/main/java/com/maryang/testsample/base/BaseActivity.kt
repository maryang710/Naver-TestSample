package com.maryang.testsample.base

import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable


abstract class BaseActivity : AppCompatActivity() {

    protected val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun onSupportNavigateUp(): Boolean {
        if (!super.onSupportNavigateUp())
            onBackPressed()
        return true
    }
}

abstract class BaseViewModelActivity : BaseActivity() {
    abstract val viewModel: BaseViewModel

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }
}
