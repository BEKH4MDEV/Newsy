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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.bekhamdev.newsy.core.domain.utils.ArticleCategory
import com.bekhamdev.newsy.main.presentation.home.components.HomeTopBar
import com.bekhamdev.newsy.main.presentation.home.components.discoverItems
import com.bekhamdev.newsy.main.presentation.home.components.headlineItems

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
    val scope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

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
                scope = scope,
                headlineArticles = headlineArticles,
                snackbarHostState = snackBarHostState,
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
                scope = scope,
                state = state,
                categories = ArticleCategory.entries,
                discoverArticles = discoverArticles,
                snackbarHostState = snackBarHostState,
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