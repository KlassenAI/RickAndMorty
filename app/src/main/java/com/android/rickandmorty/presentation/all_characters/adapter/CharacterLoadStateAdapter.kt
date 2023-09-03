package com.android.rickandmorty.presentation.all_characters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.rickandmorty.databinding.LayoutCharacterLoadStateBinding

class CharacterLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<CharacterLoadStateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder(
            LayoutCharacterLoadStateBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class ViewHolder(
        private val binding: LayoutCharacterLoadStateBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                pbLoader.isVisible = loadState is LoadState.Loading
                llRetryContainer.isVisible = loadState !is LoadState.Loading
            }
        }
    }
}