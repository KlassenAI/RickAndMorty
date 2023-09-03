package com.android.rickandmorty.presentation.core.fragments

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import com.android.rickandmorty.core.extensions.launchObserverWhenFragmentStarted
import com.android.rickandmorty.presentation.core.viewmodel.BaseViewModel

abstract class BaseViewModelFragment<VB : ViewBinding, S, VM : BaseViewModel<S>>(
    contentLayoutId: Int
) : BaseFragment<VB>(contentLayoutId) {

    protected abstract val viewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStateObserver()
    }

    private fun initStateObserver() {
        launchObserverWhenFragmentStarted {
            viewModel.state.collect {
                renderState(it)
            }
        }
    }

    abstract suspend fun renderState(state: S)
}