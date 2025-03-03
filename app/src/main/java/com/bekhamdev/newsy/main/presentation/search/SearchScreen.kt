package com.bekhamdev.newsy.main.presentation.search

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ScreenSearchDesktop
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.bekhamdev.newsy.main.presentation.GlobalAction
import com.bekhamdev.newsy.main.presentation.components.ArticleItem
import com.bekhamdev.newsy.main.presentation.components.HandlePagingErrors
import com.bekhamdev.newsy.main.presentation.model.ArticleUi
import com.bekhamdev.newsy.main.presentation.search.components.InputSearch
import com.bekhamdev.newsy.main.presentation.search.components.SearchTopBar
import com.bekhamdev.newsy.ui.theme.NewsyTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<ArticleUi>,
    onSearchAction: (SearchAction) -> Unit,
    onGlobalAction: (GlobalAction) -> Unit,
    goBack: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val loadStateMediator = articles.loadState.mediator
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    HandlePagingErrors(
        loadStates = listOf(loadStateMediator),
        snackbarHostStates = listOf(snackbarHostState)
    )

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            SearchTopBar(
                title = "Explore",
                goBack = goBack
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = NewsyTheme.dimens.defaultPadding)
        ) {
            val query = rememberSaveable {
                mutableStateOf("")
            }

            InputSearch(
                query = query,
                onSearch = {
                    onSearchAction(SearchAction.OnSearch(query.value))
                    onSearchAction(SearchAction.OnClearArticles)
                }
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = NewsyTheme.dimens.defaultPadding)
            ) {
                val loadStateRefresh = loadStateMediator?.refresh
                val isLoading = articles.loadState.append is LoadState.Loading

                item("No Articles") {
                    if (articles.itemCount == 0 && loadStateRefresh !is LoadState.Loading) {
                        Column(
                            modifier = Modifier
                                .fillParentMaxSize()
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Spacer(modifier = Modifier.weight(.3f))
                            Icon(
                                imageVector = Icons.Default.ScreenSearchDesktop,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(150.dp),
                                tint = MaterialTheme.colorScheme.outlineVariant
                            )
                            Text(
                                text = "Try searching for something",
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.outlineVariant,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }

                item(key = "Refresh Loading") {
                    if (loadStateRefresh is LoadState.Loading) {
                        Box(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .padding(top = 24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                if (loadStateRefresh !is LoadState.Loading) {
                    items(articles.itemCount, key = { articles[it]?.url ?: it }) {
                        val article = articles[it]
                        article?.let {
                            ArticleItem(
                                article = article,
                                onFavouriteChange = { article ->
                                    onGlobalAction(
                                        GlobalAction.OnFavoriteChange(
                                            article
                                        )
                                    )
                                },
                                onClick = {
                                    onGlobalAction(
                                        GlobalAction.OnArticleClick(
                                            article
                                        )
                                    )
                                },
                                sharedTransitionScope = sharedTransitionScope,
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                        }
                    }
                }

                item(
                    "Append Loading"
                ) {
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