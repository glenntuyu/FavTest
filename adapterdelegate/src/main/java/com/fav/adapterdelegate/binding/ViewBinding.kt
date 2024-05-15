package com.fav.adapterdelegate.binding

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Created by glenntuyu on 15/05/2024.
 */
object ViewBinding {
    @BindingAdapter("app:shouldShowView")
    @JvmStatic
    fun shouldShowView(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }
}