package com.maryang.testsample.util.extension

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment


fun Activity.toast(@StringRes messageResId: Int) =
    showToast(messageResId)

fun Activity.toast(message: String) =
    showToast(message)

fun Fragment.toast(@StringRes messageResId: Int) =
    requireContext().showToast(messageResId)

fun Fragment.toast(message: String) =
    requireContext().showToast(message)

fun Context.showToast(@StringRes messageResId: Int) =
    Toast.makeText(this, messageResId, Toast.LENGTH_LONG).show()

fun Context.showToast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
