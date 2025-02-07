package com.bekhamdev.newsy.main.presentation.headline

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.bekhamdev.newsy.main.presentation.GlobalAction
import com.bekhamdev.newsy.main.presentation.components.ArticleItem
import com.bekhamdev.newsy.main.presentation.components.HandlePagingErrors
import com.bekhamdev.newsy.main.presentation.components.HeaderTitle
import com.bekhamdev.newsy.main.presentation.components.ItemsPlaceholder
import com.bekhamdev.newsy.main.presentation.components.TopBar
import com.bekhamdev.newsy.main.presentation.model.ArticleUi
import com.bekhamdev.newsy.ui.theme.NewsyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeadlineScreen(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<ArticleUi>,
    onSearchClick: () -> Unit = {},
    goBack: () -> Unit = {},
    onGlobalAction: (GlobalAction) -> Unit
) {
    val loadStateHeadline = articles.loadState.mediator
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    HandlePagingErrors(
        loadStates = listOf(
            loadStateHeadline
        ),
        snackbarHostStates = listOf(
            snackBarHostState
        )
    )

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState
            )
        },
        topBar = {
            TopBar(
                onSearchClick = onSearchClick,
                goBack = goBack,
                title = {
                    HeaderTitle(
                        title = "Hot News",
                        icon = Icons.Default.LocalFireDepartment
                    )
                }
            )
        }
    ) { paddingValues ->

        var isRefreshing by remember {
            mutableStateOf(false)
        }
        val pullToRefreshState = rememberPullToRefreshState()

        val stateRefresh = articles.loadState.refresh

        LaunchedEffect(
            stateRefresh
        ) {
            if (stateRefresh !is LoadState.Loading) {
                isRefreshing = false
            }
        }

        PullToRefreshBox(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            isRefreshing = isRefreshing,
            state = pullToRefreshState,
            onRefresh = {
                isRefreshing = true
                articles.refresh()
            },
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                val isLoading = articles.loadState.append is LoadState.Loading

                item {
                    when {
                        articles.itemCount == 0 -> {
                            ItemsPlaceholder()
                        }
                    }
                }

                items(articles.itemCount) {
                    val article = articles[it]
                    article?.let {
                        ArticleItem(
                            article = article,
                            onClick = { item ->
                                onGlobalAction(
                                    GlobalAction.OnArticleClick(item)
                                )
                            },
                            onFavouriteChange = { item ->
                                onGlobalAction(
                                    GlobalAction.OnFavoriteChange(item)
                                )
                            },
                            modifier = Modifier
                                .padding(horizontal = NewsyTheme.dimens.defaultPadding)
                        )
                    }
                }

                item {
                    if (isLoading && articles.itemCount > 0) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(
                                    vertical = NewsyTheme.dimens.itemPadding
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(25.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}