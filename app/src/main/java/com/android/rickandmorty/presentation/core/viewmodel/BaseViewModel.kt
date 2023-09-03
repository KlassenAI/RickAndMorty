package com.android.rickandmorty.presentation.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<S>(initialState: S): ViewModel() {

    protected val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    protected fun updateState(update: S.() -> S) {
        _state.value = update(_state.value)
    }

    protected fun invokeSuspend(function: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { function() }
    }
}