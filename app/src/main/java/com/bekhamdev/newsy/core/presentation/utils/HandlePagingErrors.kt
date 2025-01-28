package com.bekhamdev.newsy.core.presentation.utils

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.paging.LoadState
import androidx.paging.LoadStates
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun HandlePagingErrors(
    loadStates: List<LoadStates?>,
    snackbarHostState: SnackbarHostState
) {
    val channel = remember {
        Channel<String>(Channel.CONFLATED)
    }

    var lastError by remember {
        mutableStateOf("")
    }

    LaunchedEffect(lastError) {
        if (lastError.isNotEmpty()) {
            delay(5000L)
            lastError = ""
        }
    }

    LaunchedEffect(loadStates) {
        loadStates.forEach {
            it?.let {
                val (refresh, prepend, append) = it
                when {
                    refresh is LoadState.Error -> {
                        val error = refresh.error.message ?: "Unknown Error"
                        if (lastError != error) {
                            channel.send(
                                error
                            )
                            lastError = error
                        }
                    }

                    prepend is LoadState.Error -> {
                        val error = prepend.error.message ?: "Unknown Error"
                        if (lastError != error) {
                            channel.send(
                                error
                            )
                            lastError = error
                        }
                    }

                    append is LoadState.Error -> {
                        val error = append.error.message ?: "Unknown Error"
                        if (lastError != error) {
                            channel.send(
                                error
                            )
                            lastError = error
                        }
                    }
                }
            }
        }
    }

    ObserveAsEvents(
        events = channel.receiveAsFlow()
    ) {
        snackbarHostState.showSnackbar(it)
    }
}