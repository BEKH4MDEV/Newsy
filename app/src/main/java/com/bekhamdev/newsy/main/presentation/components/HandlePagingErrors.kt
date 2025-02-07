package com.bekhamdev.newsy.main.presentation.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.paging.LoadState
import androidx.paging.LoadStates
import com.bekhamdev.newsy.core.presentation.utils.ObserveAsEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@Composable
fun HandlePagingErrors(
    loadStates: List<LoadStates?>,
    snackbarHostStates: List<SnackbarHostState>,
    indexOfUnified: Int? = null,
    singleSnackbar: Boolean = false
) {

    val errorInfo = remember {
        Channel<ErrorInfo>()
    }

    var lastError by remember { mutableStateOf("") }

    LaunchedEffect(lastError) {
        if (lastError.isNotEmpty()) {
            delay(5000L)
            lastError = ""
        }
    }

    LaunchedEffect(loadStates) {
        loadStates.forEachIndexed { index, loadState ->
            loadState?.let { (refresh, prepend, append) ->
                val errorMessage = when {
                    refresh is LoadState.Error -> refresh.error.message ?: "Unknown Error"
                    prepend is LoadState.Error -> prepend.error.message ?: "Unknown Error"
                    append is LoadState.Error -> append.error.message ?: "Unknown Error"
                    else -> null
                }
                errorMessage?.let { error ->
                    if (lastError != error) {
                        errorInfo.send(
                            ErrorInfo(message = error, index = index)
                        )
                        lastError = error
                    }
                }
            }
        }
    }

    val scope = rememberCoroutineScope()

    ObserveAsEvents(
        events = errorInfo.receiveAsFlow()
    ) {
        if (singleSnackbar) {
            scope.launch {
                snackbarHostStates[0].showSnackbar(it.message)
            }
        } else {
            scope.launch {
                snackbarHostStates[it.index].currentSnackbarData?.dismiss()
                snackbarHostStates[it.index].showSnackbar(it.message)
            }

            if (indexOfUnified != null && indexOfUnified != it.index) {
                scope.launch {
                    snackbarHostStates[indexOfUnified].currentSnackbarData?.dismiss()
                    snackbarHostStates[indexOfUnified].showSnackbar(it.message)
                }
            }
        }


    }
}

private data class ErrorInfo (
    val message: String,
    val index: Int
)