package com.android.rickandmorty.presentation.all_characters.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.rickandmorty.R
import com.android.rickandmorty.core.extensions.bind
import com.android.rickandmorty.core.extensions.loadImage
import com.android.rickandmorty.core.extensions.setStartColoredDrawable
import com.android.rickandmorty.databinding.ItemCharacterBinding
import com.android.rickandmorty.domain.character.entities.entity.CharacterEntity
import com.android.rickandmorty.domain.character.entities.ui.CharacterListItem

class CharacterPagingAdapter(
    private val clickCharacter: (characterId: Int) -> Unit,
    private val updateIsFavorite: (characterId: Int, isFavorite: Boolean, characterName: String) -> Unit
) : PagingDataAdapter<CharacterListItem, CharacterPagingAdapter.ViewHolder>(COMPARATOR) {

    companion object {

        private val COMPARATOR = object : DiffUtil.ItemCallback<CharacterListItem>() {

            override fun areItemsTheSame(oldItem: CharacterListItem, newItem: CharacterListItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CharacterListItem, newItem: CharacterListItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = getItem(position) ?: return
        holder.bind(character)
    }

    inner class ViewHolder(
        private val binding: ItemCharacterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(character: CharacterListItem) = with(binding) {

            itemView.setOnClickListener { clickCharacter.invoke(character.id) }
            ivFavorite.setOnClickListener {
                updateIsFavorite.invoke(character.id, character.isFavorite, character.name)
            }

            binding.bind(
                image = character.image,
                name = character.name,
                status = character.status,
                location = character.location,
                statusColor = character.statusColor,
                isFavorite = character.isFavorite,
            )
        }
    }
}

private fun ImageView.setBackgroundTint(color: Int) {
    this.backgroundTintList = ColorStateList.valueOf(color)
}