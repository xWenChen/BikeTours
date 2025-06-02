package com.mustly.biketours.util

import com.mustly.biketours.MainApplication

val Int.dimenRes: Int
    get() = MainApplication.context.resources.getDimensionPixelSize(this)

val Int.stringRes: String
    get() = MainApplication.context.resources.getString(this)