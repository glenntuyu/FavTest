package com.fav.adapterdelegate.util

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 * Created by glenntuyu on 15/05/2024.
 */
object ViewBindings {

    @BindingAdapter("android:showTextIfNotEmpty")
    @JvmStatic
    fun showTextIfNotEmpty(textView: TextView, text: String?) {
        textView.setTextAndCheckShow(text)
    }

    @BindingAdapter("app:shouldShowView")
    @JvmStatic
    fun shouldShowView(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }
}