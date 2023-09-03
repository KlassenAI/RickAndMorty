package com.android.rickandmorty.core.recycler

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

object DiffUtilCallback : DiffUtil.ItemCallback<ListItem>() {

    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.getId() == newItem.getId()
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }
}