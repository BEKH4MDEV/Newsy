package com.bekhamdev.newsy.main.presentation.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.bekhamdev.newsy.core.domain.utils.ArticleCategory
import com.bekhamdev.newsy.main.presentation.model.ArticleUi
import com.bekhamdev.newsy.ui.theme.NewsyTheme

fun LazyListScope.discoverItems(
    selectedDiscoverCategory: ArticleCategory,
    categories: List<ArticleCategory>,
    discoverArticles: LazyPagingItems<ArticleUi>,
    onItemClick: (ArticleUi) -> Unit,
    onCategoryChange: (ArticleCategory) -> Unit,
    onFavouriteDiscoverChange: (ArticleUi) -> Unit
) {
    val loadStateMediator = discoverArticles.loadState.mediator?.refresh
    val isLoading = discoverArticles.loadState.append is LoadState.Loading

    item {
        HeaderTitle(
            title = "Discover News",
            icon = Icons.Default.Newspaper
        )
        Spacer(
            modifier = Modifier.size(NewsyTheme.dimens.itemSpacing)
        )
        DiscoverShips(
            selectCategory = selectedDiscoverCategory,
            categories = categories,
            onCategoryChange = onCategoryChange,
            modifier = Modifier
                .padding(
                    horizontal = NewsyTheme.dimens.defaultPadding
                )
        )
    }

    item {
        when {
            loadStateMediator is LoadState.Loading
                    && discoverArticles.itemCount == 0 -> {
                DiscoverItemsPlaceholder()
            }

            loadStateMediator is LoadState.Error
                    && discoverArticles.itemCount == 0 -> {
                DiscoverItemsPlaceholder()
            }
        }
    }

    items(discoverArticles.itemCount) { index ->
        val article = discoverArticles[index]
        article?.let {
            DiscoverArticleItem(
                article = it,
                onClick = onItemClick,
                onFavouriteChange = onFavouriteDiscoverChange,
                modifier = Modifier
                    .padding(horizontal = NewsyTheme.dimens.defaultPadding)
            )
        }
    }

    item {
        if (isLoading) {
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
                        .size(20.dp)
                )
            }
        }
    }
}