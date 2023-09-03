package com.android.rickandmorty.presentation.favorite_characters

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.viewModelScope
import com.android.rickandmorty.R
import com.android.rickandmorty.domain.character.use_cases.GetFavoriteCharacterListItemsUseCase
import com.android.rickandmorty.domain.character.use_cases.SwitchCharacterIsFavoriteUseCase
import com.android.rickandmorty.presentation.core.viewmodel.BaseEventViewModel
import com.android.rickandmorty.presentation.detail_character.DetailCharacterViewModel
import com.android.rickandmorty.presentation.favorite_characters.FavoriteCharactersEvents.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class FavoriteCharactersViewModel(
    private val context: Context,
    private val getFavoriteCharactersUseCase: GetFavoriteCharacterListItemsUseCase,
    private val updateCharacterToFavoriteUseCase: SwitchCharacterIsFavoriteUseCase
) : BaseEventViewModel<FavoriteCharactersState, FavoriteCharactersEvents>(FavoriteCharactersState()) {

    init {
        getFavoriteCharacters()
    }

    private fun getFavoriteCharacters() {
        viewModelScope.launch {
            getFavoriteCharactersUseCase()
                .flowOn(Dispatchers.IO)
                .collect { characters ->
                    updateState { copy(characters = characters) }
                }
        }
    }

    fun showDialogForDeletingCharacterFromFavorite(
        characterId: Int,
        characterName: String
    ) {
        sendEvent(
            ShowDialogForDeletingCharacterFromFavoriteImpl(
                title = context.getString(R.string.dialog_title_delete_character_from_favorite),
                message = context.getString(
                    R.string.dialog_message_delete_character_from_favorite, characterName
                ),
                positiveBtnTitle = context.getString(R.string.yes),
                negativeBtnTitle = context.getString(R.string.no),
                onSuccess = { deleteCharacterFromFavorite(characterId) }
            )
        )
    }

    private fun deleteCharacterFromFavorite(characterId: Int) {
        invokeSuspend { updateCharacterToFavoriteUseCase(characterId) }
    }

    fun navigateToCharacterDetail(characterId: Int) {
        val actionId = R.id.action_to_detail_fragment
        val characterIdWithKey = DetailCharacterViewModel.ARG_CHARACTER_ID to characterId
        sendEvent(NavigateToDetailCharacterImpl(actionId, characterIdWithKey))
    }
}