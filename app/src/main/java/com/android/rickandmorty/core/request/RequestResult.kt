package com.android.rickandmorty.core.request

sealed class RequestResult<T> {

    data class Success<T>(val data: T) : RequestResult<T>()

    data class Error<T>(val message: String) : RequestResult<T>()
}