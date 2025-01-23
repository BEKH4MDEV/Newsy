package com.bekhamdev.newsy.main.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.bekhamdev.newsy.core.domain.utils.ArticleCategory
import com.bekhamdev.newsy.main.presentation.home.components.HomeTopBar
import com.bekhamdev.newsy.main.presentation.home.components.discoverItems
import com.bekhamdev.newsy.main.presentation.home.components.headlineItems
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onViewMoreClick: () -> Unit,
    onItemClick: (String) -> Unit,
    openDrawer: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val headlineArticles = state.headlineArticles.collectAsLazyPagingItems()
    val discoverArticles = state.discoverArticles.collectAsLazyPagingItems()
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val loadStateHeadline = headlineArticles.loadState.mediator
    val loadStateDiscover = discoverArticles.loadState.mediator
    val scope = rememberCoroutineScope()

    //I know what you think when you see two LaunchedEffects doing the same thing but it is necessary
    LaunchedEffect(loadStateDiscover) {
        val currentSnackbarData = snackBarHostState.currentSnackbarData
        if (currentSnackbarData == null) {
            if (loadStateDiscover?.hasError == true) {
                val (refresh, prepend, append) = loadStateDiscover
                when {
                    refresh is LoadState.Error -> {
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                message = refresh.error.message ?: "Unknown error"
                            )
                        }
                    }

                    prepend is LoadState.Error -> {
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                message = prepend.error.message ?: "Unknown error"
                            )
                        }
                    }

                    append is LoadState.Error -> {
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                message = append.error.message ?: "Unknown error"
                            )
                        }
                    }
                }
            }
        }
    }
    //I know what you think when you see two LaunchedEffects doing the same thing but it is necessary
    LaunchedEffect(loadStateHeadline) {
        val currentSnackbarData = snackBarHostState.currentSnackbarData
        if (currentSnackbarData == null) {
            if (loadStateHeadline?.hasError == true) {
                val (refresh, prepend, append) = loadStateHeadline
                when {
                    refresh is LoadState.Error -> {
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                message = refresh.error.message ?: "Unknown error"
                            )
                        }
                    }

                    prepend is LoadState.Error -> {
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                message = prepend.error.message ?: "Unknown error"
                            )
                        }
                    }

                    append is LoadState.Error -> {
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                message = append.error.message ?: "Unknown error"
                            )
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        },
        topBar = {
            HomeTopBar(
                openDrawer = openDrawer,
                onSearch = onSearchClick,
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            headlineItems(
                headlineArticles = headlineArticles,
                onViewMoreClick = onViewMoreClick,
                onItemClick = onItemClick,
                onFavouriteHeadlineChange = {
                    viewModel.onAction(
                        HomeAction.OnHeadlineFavouriteChange(
                            article = it
                        )
                    )
                }
            )

            discoverItems(
                state = state,
                categories = ArticleCategory.entries,
                discoverArticles = discoverArticles,
                onItemClick = onItemClick,
                onCategoryChange = {
                    viewModel.onAction(
                        HomeAction.OnCategoryChange(
                            category = it
                        )
                    )
                },
                onFavouriteChange = {
                    viewModel.onAction(
                        HomeAction.OnDiscoverFavouriteChange(
                            article = it
                        )
                    )
                }
            )
        }
    }
}