package com.android.rickandmorty.core.recycler

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.paging.PagedListDelegationAdapter

class BaseListItemPagingAdapter(
    vararg adapterDelegates: AdapterDelegate<List<ListItem>>
) : PagedListDelegationAdapter<ListItem>(DiffUtilCallback) {

    init {
        adapterDelegates.forEach { delegatesManager.addDelegate(it) }
    }
}


