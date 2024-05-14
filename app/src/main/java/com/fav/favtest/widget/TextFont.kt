package com.fav.favtest.widget

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatTextView
import com.fav.favtest.R

/**
 * Created by glenntuyu on 14/05/2024.
 */
class TextFont : AppCompatTextView {

    constructor(@NonNull context: Context) : super(context) {
        init()
    }

    constructor(@NonNull context: Context, @Nullable attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        init(attrs)
    }

    constructor(
        @NonNull context: Context,
        @Nullable attrs: AttributeSet?,
        @AttrRes defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet? = null) {
        val array = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.TextFont,
            0, 0
        )
        val typefaceType: Int
        try {
            typefaceType = array.getInteger(R.styleable.TextFont_typeface, 0)
        } finally {
            array.recycle()
        }

        setCustomFont(typefaceType)
        includeFontPadding = false
    }

    fun setCustomFont(typefaceType: Int) {
        typeface = TypeFace.getTypeFace(
            this.context,
            typefaceType
        )
    }
}