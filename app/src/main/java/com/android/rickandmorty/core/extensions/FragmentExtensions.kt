package com.android.rickandmorty.core.extensions

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

fun Fragment.launchObserverWhenFragmentStarted(block: suspend () -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            block.invoke()
        }
    }
}

fun Fragment.showAlertDialog(
    title: String,
    message: String,
    positiveBtnTitle: String,
    negativeBtnTitle: String,
    onSuccess: () -> Unit
) {
    AlertDialog.Builder(requireContext())
        .setTitle(title)
        .setMessage(message)
        .setNegativeButton(negativeBtnTitle, null)
        .setPositiveButton(positiveBtnTitle) { _, _ -> onSuccess.invoke() }
        .show()
}