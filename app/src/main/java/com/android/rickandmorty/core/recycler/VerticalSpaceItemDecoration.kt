package com.android.rickandmorty.core.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class VerticalSpaceItemDecoration(private val height: Int) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) != 0) {
            outRect.top = height
        }
    }
}