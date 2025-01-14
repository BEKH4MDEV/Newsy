package com.bekhamdev.newsy.main.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.bekhamdev.newsy.main.presentation.home.components.HomeTopBar
import com.bekhamdev.newsy.main.presentation.home.components.headlineItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onViewMoreClick: () -> Unit,
    onHeadlineItemClick: (id: Long) -> Unit,
    openDrawer: () -> Unit,
    onSearchClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val articles = state.headlineArticles.collectAsLazyPagingItems()
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        },
        topBar = {
            HomeTopBar(
                openDrawer = openDrawer,
                onSearch = onSearchClick
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            headlineItems(
                headlineArticles = articles,
                snackbarHostState = snackBarHostState,
                onViewMoreClick = onViewMoreClick,
                onHeadlineItemClick = onHeadlineItemClick,
                onFavouriteHeadlineChange = {
                    viewModel.onEvent(
                        HomeEvents.OnHeadlineFavouriteChange(
                            article = it
                        )
                    )
                }
            )

        }
    }
}