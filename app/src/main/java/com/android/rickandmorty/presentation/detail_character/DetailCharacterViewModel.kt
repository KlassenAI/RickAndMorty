package com.android.rickandmorty.presentation.detail_character

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.android.rickandmorty.R
import com.android.rickandmorty.core.request.RequestResult
import com.android.rickandmorty.domain.character.use_cases.GetDetailCharacterItemByIdUseCase
import com.android.rickandmorty.domain.character.use_cases.SwitchCharacterIsFavoriteUseCase
import com.android.rickandmorty.presentation.core.viewmodel.BaseEventViewModel
import com.android.rickandmorty.presentation.detail_character.DetailCharacterEvents.ShowDialogForDeletingCharacterFromFavoriteImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class DetailCharacterViewModel(
    handle: SavedStateHandle,
    private val context: Context,
    private val getCharacterByIdUseCase: GetDetailCharacterItemByIdUseCase,
    private val switchCharacterIsFavoriteUseCase: SwitchCharacterIsFavoriteUseCase,
) : BaseEventViewModel<DetailCharacterState, DetailCharacterEvents>(DetailCharacterState()) {

    companion object {
        const val ARG_CHARACTER_ID = "characterId"
        private const val EXCEPTION_NO_CHARACTER_ID_PASSED = "No Character Id Passed"
    }

    private val characterId: Int =
        handle[ARG_CHARACTER_ID] ?: throw Exception(EXCEPTION_NO_CHARACTER_ID_PASSED)

    init {
        viewModelScope.launch {
            getCharacterByIdUseCase(characterId)
                .flowOn(Dispatchers.IO)
                .collectLatest { result ->
                    when (result) {
                        is RequestResult.Error -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                errorMessage = result.message,
                                character = null
                            )
                        }
                        is RequestResult.Success -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                errorMessage = "",
                                character = result.data
                            )
                        }
                    }
                }
        }
    }

    fun tryToSwitchCharacterIsFavorite() {

        val character = _state.value.character ?: return

        if (!character.isFavorite) {
            switchCharacterIsFavorite()
            return
        }

        sendEvent(
            ShowDialogForDeletingCharacterFromFavoriteImpl(
                title = context.getString(R.string.dialog_title_delete_character_from_favorite),
                message = context.getString(
                    R.string.dialog_message_delete_character_from_favorite, character.name
                ),
                positiveBtnTitle = context.getString(R.string.yes),
                negativeBtnTitle = context.getString(R.string.no),
                onSuccess = ::switchCharacterIsFavorite
            )
        )
    }

    private fun switchCharacterIsFavorite() {
        invokeSuspend { switchCharacterIsFavoriteUseCase.invoke(characterId) }
    }
}