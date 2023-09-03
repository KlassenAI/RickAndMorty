package com.android.rickandmorty.presentation.all_characters

import android.util.Log
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.rickandmorty.R
import com.android.rickandmorty.core.extensions.initVerticalFixedAdapterWithSpace
import com.android.rickandmorty.core.extensions.showAlertDialog
import com.android.rickandmorty.databinding.FragmentAllCharactersBinding
import com.android.rickandmorty.presentation.all_characters.AllCharactersEvents.NavigateToDetailCharacterImpl
import com.android.rickandmorty.presentation.all_characters.AllCharactersEvents.ShowDialogForDeletingCharacterFromFavoriteImpl
import com.android.rickandmorty.presentation.all_characters.adapter.CharacterLoadStateAdapter
import com.android.rickandmorty.presentation.all_characters.adapter.CharacterPagingAdapter
import com.android.rickandmorty.presentation.core.fragments.BaseEventViewModelFragment
import com.android.rickandmorty.presentation.core.fragments.HasListeners
import com.android.rickandmorty.presentation.core.fragments.HasViews
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllCharactersFragment :
    BaseEventViewModelFragment<FragmentAllCharactersBinding, AllCharactersState, AllCharactersEvents, AllCharactersViewModel>(
        R.layout.fragment_all_characters
    ), HasViews, HasListeners {

    override val binding: FragmentAllCharactersBinding by viewBinding()
    override val viewModel: AllCharactersViewModel by viewModel()

    private val characterAdapter = CharacterPagingAdapter(
        clickCharacter = { viewModel.navigateToDetailCharacter(it) },
        updateIsFavorite = { id, isFavorite, name ->
            viewModel.tryToSwitchCharacterIsFavorite(id, isFavorite, name)
        }
    )

    override fun initViews() {
        binding.rvCharacters.initVerticalFixedAdapterWithSpace(
            adapter = characterAdapter.withLoadStateHeaderAndFooter(
                header = CharacterLoadStateAdapter { characterAdapter.retry() },
                footer = CharacterLoadStateAdapter { characterAdapter.retry() }
            ),
            spaceHeight = requireContext().resources.getDimension(R.dimen.default_padding).toInt()
        )
    }

    override fun initListeners() = with(binding) {
        characterAdapter.addLoadStateListener { state ->
            pbLoader.isVisible = state.mediator!!.refresh is LoadState.Loading
            rvCharacters.isVisible = isCharacterListVisible(state)
            llErrorContainer.isVisible = state.mediator!!.refresh is LoadState.Error
            llEmptyContainer.isVisible = isCharacterListEmpty(state)
        }
        btnRetry.setOnClickListener { characterAdapter.retry() }
        btnRetry2.setOnClickListener { characterAdapter.retry() }
    }

    private fun isCharacterListVisible(state: CombinedLoadStates): Boolean {
        return state.mediator!!.refresh is LoadState.NotLoading && !isCharacterListEmpty(state)
    }

    private fun isCharacterListEmpty(state: CombinedLoadStates): Boolean {
        return state.mediator!!.refresh is LoadState.NotLoading
                && state.mediator!!.append.endOfPaginationReached
                && characterAdapter.itemCount < 1
    }

    override suspend fun renderState(state: AllCharactersState) {
        characterAdapter.submitData(state.characters)
    }

    override fun handleEvent(event: AllCharactersEvents) {
        when (event) {
            is NavigateToDetailCharacterImpl -> {
                findNavController().navigate(event.actionId, bundleOf(event.characterIdWithKey))
            }

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