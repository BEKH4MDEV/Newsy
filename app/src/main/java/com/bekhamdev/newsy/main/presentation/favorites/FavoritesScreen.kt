package com.bekhamdev.newsy.main.presentation.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bekhamdev.newsy.core.domain.utils.ArticleCategory
import com.bekhamdev.newsy.main.presentation.GlobalAction
import com.bekhamdev.newsy.main.presentation.components.ArticleItem
import com.bekhamdev.newsy.main.presentation.components.ItemsPlaceholder
import com.bekhamdev.newsy.main.presentation.components.Ships
import com.bekhamdev.newsy.main.presentation.components.TopBar
import com.bekhamdev.newsy.main.presentation.favorites.components.ShipsPlaceholder
import com.bekhamdev.newsy.main.presentation.model.ArticleUi
import com.bekhamdev.newsy.ui.theme.NewsyTheme

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    goBack: () -> Unit,
    isLoading: Boolean,
    onFavouritesAction: (FavoritesAction) -> Unit,
    onGlobalAction: (GlobalAction) -> Unit,
    favourites: List<ArticleUi>,
    categories: List<ArticleCategory>,
    selectedCategory: ArticleCategory?
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopBar(
                title = {
                    Text(text = "Favorites")
                },
                goBack = goBack,
                isSearchVisible = false
            )
        }
    ) { paddingValues ->
        val scrollState = rememberScrollState()

        LaunchedEffect(categories) {
            if (selectedCategory == categories.firstOrNull()) {
                scrollState.animateScrollTo(0)
            }
        }
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = NewsyTheme.dimens.defaultPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                if (isLoading) {
                    ShipsPlaceholder()
                    Spacer(modifier = Modifier.height(NewsyTheme.dimens.itemSpacing))
                    ItemsPlaceholder()
                } else if (categories.isEmpty()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillParentMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bookmarks,
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp),
                            tint = MaterialTheme.colorScheme.secondaryContainer
                        )
                        Spacer(modifier = Modifier.height(NewsyTheme.dimens.defaultPadding))
                        Text(
                            text = "No favorites found",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.secondaryContainer
                        )
                    }
                }
            }

            item {
                if (categories.isNotEmpty() && selectedCategory != null) {
                    Ships(
                        selectCategory = selectedCategory,
                        categories = categories,
                        onCategoryChange = {
                            onFavouritesAction(FavoritesAction.OnCategoryChange(it))
                        },
                        includeAll = true,
                        scrollState = scrollState
                    )
                }
            }

            items(favourites) {
                ArticleItem(
                    article = it,
                    onClick = { article ->
                        onGlobalAction(GlobalAction.OnArticleClick(article))
                    },
                    onFavouriteChange = { article ->
                        onGlobalAction(GlobalAction.OnFavoriteChange(article))
                    }
                )
            }
        }
    }
}