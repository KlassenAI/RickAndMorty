package com.android.rickandmorty.presentation.favorite_characters

import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.rickandmorty.R
import com.android.rickandmorty.core.extensions.initVerticalFixedAdapterWithSpace
import com.android.rickandmorty.core.extensions.showAlertDialog
import com.android.rickandmorty.databinding.FragmentFavoriteCharactersBinding
import com.android.rickandmorty.presentation.core.fragments.BaseEventViewModelFragment
import com.android.rickandmorty.presentation.core.fragments.HasViews
import com.android.rickandmorty.presentation.favorite_characters.FavoriteCharactersEvents.NavigateToDetailCharacterImpl
import com.android.rickandmorty.presentation.favorite_characters.FavoriteCharactersEvents.ShowDialogForDeletingCharacterFromFavoriteImpl
import com.android.rickandmorty.presentation.favorite_characters.FavoriteCharactersStateStatus.Empty
import com.android.rickandmorty.presentation.favorite_characters.FavoriteCharactersStateStatus.Success
import com.android.rickandmorty.presentation.favorite_characters.adapter.FavoriteCharacterAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteCharactersFragment :
    BaseEventViewModelFragment<FragmentFavoriteCharactersBinding, FavoriteCharactersState, FavoriteCharactersEvents, FavoriteCharactersViewModel>(
        R.layout.fragment_favorite_characters
    ), HasViews {

    override val binding: FragmentFavoriteCharactersBinding by viewBinding()
    override val viewModel: FavoriteCharactersViewModel by viewModel()

    private val favoriteCharacterAdapter = FavoriteCharacterAdapter(
        clickCharacter = { viewModel.navigateToCharacterDetail(it) },
        deleteCharacterFromFavorite = { id, name ->
            viewModel.showDialogForDeletingCharacterFromFavorite(id, name)
        }
    )

    override fun initViews() {
        binding.rvFavoriteCharacters.initVerticalFixedAdapterWithSpace(
            adapter = favoriteCharacterAdapter,
            spaceHeight = requireContext().resources.getDimension(R.dimen.default_padding).toInt()
        )
    }

    override suspend fun renderState(state: FavoriteCharactersState) = with(binding) {
        val status = state.status
        rvFavoriteCharacters.isVisible = status is Success
        tvEmptyMessage.isVisible = status is Empty
        favoriteCharacterAdapter.submitList(state.characters)
    }

    override fun handleEvent(event: FavoriteCharactersEvents) {
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
            is NavigateToDetailCharacterImpl -> {
                findNavController().navigate(event.actionId, bundleOf(event.characterIdWithKey))
            }
        }
    }
}