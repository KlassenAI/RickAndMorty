package com.android.rickandmorty.presentation.core.fragments

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import com.android.rickandmorty.core.extensions.launchObserverWhenFragmentStarted
import com.android.rickandmorty.presentation.core.viewmodel.BaseEventViewModel

abstract class BaseEventViewModelFragment<VB : ViewBinding, S, E, VM : BaseEventViewModel<S, E>>(
    contentLayoutId: Int
) : BaseViewModelFragment<VB, S, VM>(contentLayoutId) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEventsObserver()
    }

    private fun initEventsObserver() {
        launchObserverWhenFragmentStarted {
            viewModel.events.collect {
                handleEvent(it)
            }
        }
    }

    abstract fun handleEvent(event: E)
}