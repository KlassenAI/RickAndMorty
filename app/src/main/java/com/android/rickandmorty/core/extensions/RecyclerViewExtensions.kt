package com.android.rickandmorty.core.extensions

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.rickandmorty.core.recycler.VerticalSpaceItemDecoration

fun <VH : RecyclerView.ViewHolder> RecyclerView.initVerticalFixedAdapterWithSpace(
    adapter: RecyclerView.Adapter<VH>,
    spaceHeight: Int,
) {
    itemAnimator = null
    setHasFixedSize(true)
    addItemDecoration(VerticalSpaceItemDecoration(spaceHeight))
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    this.adapter = adapter
}