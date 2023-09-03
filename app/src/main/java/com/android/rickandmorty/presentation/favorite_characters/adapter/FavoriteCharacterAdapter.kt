package com.android.rickandmorty.presentation.favorite_characters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.rickandmorty.R
import com.android.rickandmorty.core.extensions.bind
import com.android.rickandmorty.core.extensions.loadImage
import com.android.rickandmorty.core.extensions.setStartColoredDrawable
import com.android.rickandmorty.databinding.ItemCharacterBinding
import com.android.rickandmorty.domain.character.entities.ui.FavoriteCharacterListItem

class FavoriteCharacterAdapter(
    private val clickCharacter: (characterId: Int) -> Unit,
    private val deleteCharacterFromFavorite: (characterId: Int, characterName: String) -> Unit
) : RecyclerView.Adapter<FavoriteCharacterAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<FavoriteCharacterListItem>() {

        override fun areItemsTheSame(
            oldItem: FavoriteCharacterListItem,
            newItem: FavoriteCharacterListItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: FavoriteCharacterListItem,
            newItem: FavoriteCharacterListItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    fun submitList(characters: List<FavoriteCharacterListItem>) {
        differ.submitList(characters)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = differ.currentList[position]
        holder.bind(character)
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(
        private val binding: ItemCharacterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(character: FavoriteCharacterListItem) = with(binding) {

            itemView.setOnClickListener { clickCharacter.invoke(character.id) }
            ivFavorite.setOnClickListener {
                deleteCharacterFromFavorite.invoke(character.id, character.name)
            }

            binding.bind(
                image = character.image,
                name = character.name,
                status = character.status,
                location = character.location,
                statusColor = character.statusColor,
                isFavorite = true,
            )
        }
    }
}