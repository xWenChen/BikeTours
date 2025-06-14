package com.mustly.biketours.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mustly.biketours.R
import com.mustly.biketours.util.dimenRes

class RecordItemDecoration() : RecyclerView.ItemDecoration() {

    private val space = R.dimen.padding_middle.dimenRes / 2

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildLayoutPosition(view)
        val count = state.itemCount
        outRect.top = space
        outRect.bottom = space
    }
}