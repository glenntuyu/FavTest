package com.fav.favtest.widget

import android.content.Context
import android.graphics.Typeface

/**
 * Created by glenntuyu on 14/05/2024.
 */
class TypeFactory internal constructor(context: Context) {
    val bold: Typeface = Typeface.createFromAsset(context.assets, BOLD)
    val italic: Typeface = Typeface.createFromAsset(context.assets, ITALIC)
    val medium: Typeface = Typeface.createFromAsset(context.assets, MEDIUM)
    val regular: Typeface = Typeface.createFromAsset(context.assets, REGULAR)

    companion object {
        private const val BOLD = "fonts/roboto_bold.ttf"
        private const val ITALIC = "fonts/roboto_italic.ttf"
        private const val MEDIUM = "fonts/roboto_medium.ttf"
        private const val REGULAR = "fonts/roboto_regular.ttf"
    }
}