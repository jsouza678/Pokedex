package com.souza.extensions

import android.view.View

fun View.visible() = View.VISIBLE

fun View.gone() {
    visibility = View.GONE
}
