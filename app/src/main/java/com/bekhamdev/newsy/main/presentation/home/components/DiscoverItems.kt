package com.bekhamdev.newsy.main.presentation.home.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.bekhamdev.newsy.core.domain.utils.ArticleCategory
import com.bekhamdev.newsy.main.presentation.home.HomeState
import com.bekhamdev.newsy.main.presentation.model.ArticleUi
import com.bekhamdev.newsy.ui.theme.NewsyTheme

fun LazyListScope.discoverItems(
    state: HomeState,
    categories: List<ArticleCategory>,
    discoverArticles: LazyPagingItems<ArticleUi>,
    onItemClick: (String) -> Unit,
    onCategoryChange: (ArticleCategory) -> Unit,
    onFavouriteChange: (ArticleUi) -> Unit
) {
    val loadStateMediator = discoverArticles.loadState.mediator?.refresh

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