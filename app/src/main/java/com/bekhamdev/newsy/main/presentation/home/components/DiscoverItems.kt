package com.bekhamdev.newsy.main.presentation.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.bekhamdev.newsy.core.domain.utils.ArticleCategory
import com.bekhamdev.newsy.main.presentation.home.HomeState
import com.bekhamdev.newsy.main.presentation.model.ArticleUi
import com.bekhamdev.newsy.ui.theme.NewsyTheme
import kotlinx.coroutines.launch

fun LazyListScope.discoverItems(
    state: HomeState,
    categories: List<ArticleCategory>,
    discoverArticles: LazyPagingItems<ArticleUi>,
    snackbarHostState: SnackbarHostState,
    onItemClick: (String) -> Unit,
    onCategoryChange: (ArticleCategory) -> Unit,
    onFavouriteChange: (ArticleUi) -> Unit,
) {
    val loadState = discoverArticles.loadState.refresh

    item {
        HeaderTitle(
            title = "Discover News",
            icon = Icons.Default.Newspaper
        )
        Spacer(
            modifier = Modifier.size(NewsyTheme.dimens.itemSpacing)
        )
        DiscoverShips(
            selectCategory = state.selectedDiscoverCategory,
            categories = categories,
            onCategoryChange = onCategoryChange,
            modifier = Modifier
                .padding(
                    horizontal = NewsyTheme.dimens.mediumPadding
                )
        )
    }

    item {
        val scope = rememberCoroutineScope()
        PaginationLoadingItem(
            loadState = loadState,
            items = discoverArticles.itemSnapshotList.items,
            onError = {
                scope.launch {
                    snackbarHostState.showSnackbar(it.message ?: "Unknown error")
                }
            },
            onLoading = {
                DiscoverItemsPlaceholder()
            }
        )
    }

    items(discoverArticles.itemSnapshotList) {
        it?.let {
            if (loadState is LoadState.NotLoading
                &&
                discoverArticles.itemSnapshotList.items.isNotEmpty()
                ) {
                DiscoverArticleItem(
                    article = it,
                    onClick = { article ->
                        onItemClick(article.url)
                    },
                    onFavouriteChange = onFavouriteChange,
                    modifier = Modifier
                        .padding(horizontal = NewsyTheme.dimens.mediumPadding)
                )
            }
        }
    }
}