package com.mustly.biketours.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mustly.biketours.R
import com.mustly.biketours.util.dimenRes

class DistanceItemDecoration(private val spanCount: Int) : RecyclerView.ItemDecoration() {

    private val space = R.dimen.padding_large.dimenRes / 2
    private val vSpace = R.dimen.padding_middle
    private val lastColumn = spanCount - 1

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildLayoutPosition(view)
        val columnIndex = position % spanCount
        outRect.left = space
        outRect.right = space
        outRect.top = vSpace
        if (columnIndex == 0) {
            // 首列
            outRect.left =0
        } else if (columnIndex == lastColumn) {
            // 尾列
            outRect.right = 0
        }
    }
}