package com.android.rickandmorty.presentation.all_characters

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.android.rickandmorty.R
import com.android.rickandmorty.domain.character.use_cases.GetAllCharactersUseCase
import com.android.rickandmorty.domain.character.use_cases.SwitchCharacterIsFavoriteUseCase
import com.android.rickandmorty.presentation.core.viewmodel.BaseEventViewModel
import com.android.rickandmorty.presentation.detail_character.DetailCharacterEvents
import com.android.rickandmorty.presentation.detail_character.DetailCharacterViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class AllCharactersViewModel(
    private val context: Context,
    private val getAllCharactersUseCase: GetAllCharactersUseCase,
    private val updateCharacterToFavoriteUseCase: SwitchCharacterIsFavoriteUseCase
) : BaseEventViewModel<AllCharactersState, AllCharactersEvents>(AllCharactersState()) {

    init {
        getAllCharacters()
    }

    private fun getAllCharacters() {
        viewModelScope.launch {
            getAllCharactersUseCase()
                .flowOn(Dispatchers.IO)
                .cachedIn(viewModelScope)
                .collect { updateState { copy(characters = it) } }
        }
    }

    fun tryToSwitchCharacterIsFavorite(
        characterId: Int,
        isFavorite: Boolean,
        characterName: String,
    ) {

        if (!isFavorite) {
            switchCharacterIsFavorite(characterId)
            return
        }

        sendEvent(
            AllCharactersEvents.ShowDialogForDeletingCharacterFromFavoriteImpl(
                title = context.getString(R.string.dialog_title_delete_character_from_favorite),
                message = context.getString(
                    R.string.dialog_message_delete_character_from_favorite, characterName
                ),
                positiveBtnTitle = context.getString(R.string.yes),
                negativeBtnTitle = context.getString(R.string.no),
                onSuccess = { switchCharacterIsFavorite(characterId) }
            )
        )
    }

    private fun switchCharacterIsFavorite(characterId: Int) {
        invokeSuspend { updateCharacterToFavoriteUseCase(characterId) }
    }

    fun navigateToDetailCharacter(characterId: Int) {
        val actionId = R.id.action_to_detail_fragment
        val characterIdWithKey = DetailCharacterViewModel.ARG_CHARACTER_ID to characterId
        sendEvent(AllCharactersEvents.NavigateToDetailCharacterImpl(actionId, characterIdWithKey))
    }
}