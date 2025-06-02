package com.mustly.biketours.util

import android.view.View

class NoDoubleClickListener(
    val noDoubleClick: (View?) -> Unit
) : View.OnClickListener {
    var startTime = 0L
    override fun onClick(v: View?) {
        val nowTime = System.currentTimeMillis()
        if (nowTime - startTime > 500) {
            noDoubleClick(v)
            startTime = nowTime
        }
    }
}

fun View?.setNoDoubleClickListener(onClick: (View?) -> Unit) {
    this?.setOnClickListener(NoDoubleClickListener(onClick))
}