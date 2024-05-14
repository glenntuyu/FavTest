package com.fav.favtest.widget

import android.content.Context
import android.graphics.Typeface

/**
 * Created by glenntuyu on 14/05/2024.
 */
object TypeFace {
    private const val REGULAR = 0
    private const val MEDIUM = 1
    private const val BOLD = 2
    private const val ITALIC = 3

    private var mFontFactory: TypeFactory? = null

    fun getTypeFace(context: Context, type: Int): Typeface {
        if (mFontFactory == null) mFontFactory = TypeFactory(context)

        return when (type) {
            MEDIUM -> mFontFactory!!.medium
            BOLD -> mFontFactory!!.bold
            ITALIC -> mFontFactory!!.italic
            else -> mFontFactory!!.regular
        }
    }
}