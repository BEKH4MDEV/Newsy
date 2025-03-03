package com.bekhamdev.newsy.main.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.bekhamdev.newsy.core.domain.utils.ArticleCategory
import com.bekhamdev.newsy.main.presentation.GlobalAction
import com.bekhamdev.newsy.main.presentation.components.HandlePagingErrors
import com.bekhamdev.newsy.main.presentation.home.components.HomeTopBar
import com.bekhamdev.newsy.main.presentation.home.components.discoverItems
import com.bekhamdev.newsy.main.presentation.home.components.headlineItems
import com.bekhamdev.newsy.main.presentation.model.ArticleUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    onViewMoreClick: () -> Unit,
    openDrawer: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
    onHomeAction: (HomeAction) -> Unit,
    onGlobalAction: (GlobalAction) -> Unit,
    headlineArticles: LazyPagingItems<ArticleUi>,
    discoverArticles: LazyPagingItems<ArticleUi>
) {
    val loadStateHeadline = headlineArticles.loadState.mediator
    val loadStateDiscover = discoverArticles.loadState.mediator
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    HandlePagingErrors(
        loadStates = listOf(
            loadStateHeadline,
            loadStateDiscover
        ),
        snackbarHostStates = listOf(
            snackBarHostState
        ),
        singleSnackbar = true
    )

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

        var isRefreshing by remember {
            mutableStateOf(false)
        }
        val pullToRefreshState = rememberPullToRefreshState()
        val headlineStateRefresh = headlineArticles.loadState.refresh
        val discoverStateRefresh = discoverArticles.loadState.refresh

        LaunchedEffect(
            key1 = headlineStateRefresh,
            key2 = discoverStateRefresh
        ) {
            if (headlineStateRefresh !is LoadState.Loading
                && discoverStateRefresh !is LoadState.Loading) {
                isRefreshing = false
            }
        }

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            state = pullToRefreshState,
            onRefresh = {
                isRefreshing = true
                headlineArticles.refresh()
                discoverArticles.refresh()
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val listState = rememberLazyListState()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                state = listState
            ) {
                headlineItems(
                    headlineArticles = headlineArticles,
                    onViewMoreClick = onViewMoreClick,
                    onItemClick = {
                        onGlobalAction(
                            GlobalAction.OnArticleClick(
                                it
                            )
                        )
                    },
                    onFavouriteHeadlineChange = {
                        onGlobalAction(
                            GlobalAction.OnFavoriteChange(
                                it
                            )
                        )
                    },
                    listState = listState
                )

                discoverItems(
                    selectedDiscoverCategory = state.selectedDiscoverCategory,
                    categories = ArticleCategory.entries,
                    discoverArticles = discoverArticles,
                    onItemClick = {
                        onGlobalAction(
                            GlobalAction.OnArticleClick(
                                it
                            )
                        )
                    },
                    onCategoryChange = {
                        onHomeAction(
                            HomeAction.OnCategoryChange(it)
                        )
                    },
                    onFavouriteDiscoverChange = {
                        onGlobalAction(
                            GlobalAction.OnFavoriteChange(
                                it
                            )
                        )
                    }
                )
            }
        }
    }
}