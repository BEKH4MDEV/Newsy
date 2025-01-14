package com.bekhamdev.newsy.main.presentation.home.components

import androidx.compose.runtime.Composable
import androidx.paging.LoadState

@Composable
fun PaginationLoadingItem(
    loadState: LoadState?,
    onError: (e: Throwable) -> Unit,
    onLoading: @Composable () -> Unit,
) {
    when (loadState) {
        is LoadState.Error -> {
            onError(loadState.error)
        }
        is LoadState.Loading -> {
            onLoading()
        }
        else -> {}
    }
}