package com.android.rickandmorty.presentation.core.fragments

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB: ViewBinding>(
    @LayoutRes private val contentLayoutId: Int
): Fragment(contentLayoutId) {

    protected abstract val binding: VB

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (this is HasViews) {
            initViews()
        }
        if (this is HasListeners) {
            initListeners()
        }
    }
}