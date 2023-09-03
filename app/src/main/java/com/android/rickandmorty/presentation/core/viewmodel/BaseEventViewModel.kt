package com.android.rickandmorty.presentation.core.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseEventViewModel<S, E>(initialState: S): BaseViewModel<S>(initialState) {

    private val _events: Channel<E> = Channel()
    val events: Flow<E> = _events.receiveAsFlow()

    fun sendEvent(event: E) {
        viewModelScope.launch { _events.send(event) }
    }
}