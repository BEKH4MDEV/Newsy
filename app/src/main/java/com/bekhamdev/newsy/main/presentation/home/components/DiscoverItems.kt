package com.bekhamdev.newsy.main.presentation.home.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
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
import com.bekhamdev.newsy.main.presentation.components.ArticleItem
import com.bekhamdev.newsy.main.presentation.components.HeaderTitle
import com.bekhamdev.newsy.main.presentation.components.ItemsPlaceholder
import com.bekhamdev.newsy.main.presentation.components.Ships
import com.bekhamdev.newsy.main.presentation.model.ArticleUi
import com.bekhamdev.newsy.ui.theme.NewsyTheme

@OptIn(ExperimentalSharedTransitionApi::class)
fun LazyListScope.discoverItems(
    selectedDiscoverCategory: ArticleCategory,
    categories: List<ArticleCategory>,
    discoverArticles: LazyPagingItems<ArticleUi>,
    onItemClick: (ArticleUi) -> Unit,
    onCategoryChange: (ArticleCategory) -> Unit,
    onFavouriteDiscoverChange: (ArticleUi) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val loadStateMediator = discoverArticles.loadState.mediator?.refresh
    val isLoading = discoverArticles.loadState.append is LoadState.Loading

    item(key = "Discover Header") {
        HeaderTitle(
            title = "Discover News",
            icon = Icons.Default.Newspaper
        )
        Spacer(
            modifier = Modifier.size(NewsyTheme.dimens.itemSpacing)
        )
        Ships(
            selectCategory = selectedDiscoverCategory,
            categories = categories,
            onCategoryChange = onCategoryChange,
            modifier = Modifier
                .padding(
                    horizontal = NewsyTheme.dimens.defaultPadding
                )
        )
    }

    item(key = "Discover Placeholder") {
        when {
            loadStateMediator is LoadState.Loading
                    && discoverArticles.itemCount == 0 -> {
                ItemsPlaceholder()
            }

            loadStateMediator is LoadState.Error
                    && discoverArticles.itemCount == 0 -> {
                ItemsPlaceholder()
            }
        }
    }

    items(discoverArticles.itemCount, key = { discoverArticles[it]?.url ?: it }) { index ->
        val article = discoverArticles[index]
        article?.let {
            ArticleItem(
                article = it,
                onClick = onItemClick,
                onFavouriteChange = onFavouriteDiscoverChange,
                modifier = Modifier
                    .padding(horizontal = NewsyTheme.dimens.defaultPadding),
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope
            )
        }
    }

    item(key = "Discover Loading") {
        if (isLoading && discoverArticles.itemCount > 0) {
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