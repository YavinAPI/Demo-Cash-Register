package com.yavin.macewindu.utils.extensions

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

fun View.hideKeyboard(context: Context) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.showKeyboard(context: Context) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, 0)
}

@Deprecated(message = "We're using the deprecated method for now, because `showKeyboard(context)` doesn't seem to work reliably.")
fun View.deprecatedShowKeyboard(context: Context) {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInputFromWindow(this.applicationWindowToken, InputMethodManager.SHOW_FORCED, 0)
}

fun Int.dpToPx() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun View.setMargins(
    leftMarginDp: Int? = null,
    topMarginDp: Int? = null,
    rightMarginDp: Int? = null,
    bottomMarginDp: Int? = null
) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val params = layoutParams as ViewGroup.MarginLayoutParams
        leftMarginDp?.run { params.leftMargin = this.dpToPx() }
        topMarginDp?.run { params.topMargin = this.dpToPx() }
        rightMarginDp?.run { params.rightMargin = this.dpToPx() }
        bottomMarginDp?.run { params.bottomMargin = this.dpToPx() }
        requestLayout()
    }
}
