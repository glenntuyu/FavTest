package com.fav.favtest.util

import android.view.View
import android.widget.TextView

/**
 * Created by glenntuyu on 15/05/2024.
 */

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun TextView.setTextAndCheckShow(text: String?) {
    if (text.isNullOrEmpty()) {
        gone()
    } else {
        setText(text)
        visible()
    }
}