package com.yavin.macewindu.utils.extensions

import android.app.AlertDialog
import android.content.Context
import com.yavin.cashregister.R

fun Context.showDialogError(
    message: String,
    positive: () -> Unit
) {
    val builder = AlertDialog.Builder(this)
        .setTitle(getString(R.string.ko))
        .setCancelable(false)
        .setMessage(message)
        .setPositiveButton(getString(R.string.ok)) { _, _ -> positive() }
    val alert = builder.create()
    alert.show()
}