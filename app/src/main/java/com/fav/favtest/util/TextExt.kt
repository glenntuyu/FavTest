package com.fav.favtest.util

import android.view.View

/**
 * Created by glenntuyu on 16/05/2024.
 */
fun String.toRoomQuery(): String {
    return "%$this%"
}