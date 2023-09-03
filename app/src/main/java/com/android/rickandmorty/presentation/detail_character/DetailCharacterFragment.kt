package com.android.rickandmorty.presentation.detail_character

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.rickandmorty.R
import com.android.rickandmorty.core.extensions.loadImage
import com.android.rickandmorty.core.extensions.setStartColoredDrawable
import com.android.rickandmorty.core.extensions.showAlertDialog
import com.android.rickandmorty.databinding.FragmentDetailCharacterBinding
import com.android.rickandmorty.domain.character.entities.ui.CharacterItem
import com.android.rickandmorty.presentation.core.fragments.BaseEventViewModelFragment
import com.android.rickandmorty.presentation.core.fragments.HasListeners
import com.android.rickandmorty.presentation.core.fragments.HasViews
import com.android.rickandmorty.presentation.detail_character.DetailCharacterEvents.ShowDialogForDeletingCharacterFromFavoriteImpl
import com.android.rickandmorty.presentation.detail_character.DetailCharacterStateStatus.Error
import com.android.rickandmorty.presentation.detail_character.DetailCharacterStateStatus.Loading
import com.android.rickandmorty.presentation.detail_character.DetailCharacterStateStatus.Success
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailCharacterFragment :
    BaseEventViewModelFragment<FragmentDetailCharacterBinding, DetailCharacterState, DetailCharacterEvents, DetailCharacterViewModel>(
        R.layout.fragment_detail_character
    ), HasViews, HasListeners {

    override val binding: FragmentDetailCharacterBinding by viewBinding()
    override val viewModel: DetailCharacterViewModel by viewModel()

    override fun initViews() {
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbar)
        addBackHomeButton()
    }

    private fun addBackHomeButton() {
        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun initListeners() {
        binding.ivFavorite.setOnClickListener { viewModel.tryToSwitchCharacterIsFavorite() }
    }

    override suspend fun renderState(state: DetailCharacterState) = with(binding) {
        val status = state.status
        flLoader.isVisible = status is Loading
        flErrorMessage.isVisible = status is Error
        ivFavorite.isEnabled = status is Success
        nsvCharacterContainer.isVisible = status is Success
        if (status is Error) {
            tvErrorMessage.text = status.message
        }
        if (status is Success) {
            updateCharacterContainerView(status.character)
        }
    }

    private fun updateCharacterContainerView(character: CharacterItem) = with(binding) {
        ivFavorite.isSelected = character.isFavorite
        ivImage.loadImage(character.image)
        tvName.text = character.name
        tvStatus.text = character.status
        tvStatus.setStartColoredDrawable(
            drawableId = R.drawable.round,
            dimenRes = R.dimen.default_padding,
            color = character.statusColor
        )
        tvSpecies.text = character.species
        tvType.text = character.type
        tvGender.text = character.gender
        tvOrigin.text = character.origin
        tvLocation.text = character.location
    }

    override fun handleEvent(event: DetailCharacterEvents) {
        when (event) {
            is ShowDialogForDeletingCharacterFromFavoriteImpl -> {
                showAlertDialog(
                    title = event.title,
                    message = event.message,
                    positiveBtnTitle = event.positiveBtnTitle,
                    negativeBtnTitle = event.negativeBtnTitle,
                    onSuccess = event.onSuccess
                )
            }
        }
    }
}