package com.bekhamdev.newsy.main.presentation.home.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.paging.LoadState
import androidx.paging.LoadType
import androidx.paging.compose.LazyPagingItems
import com.bekhamdev.newsy.main.presentation.model.ArticleUi
import com.bekhamdev.newsy.ui.theme.NewsyTheme
import com.google.accompanist.placeholder.placeholder
import kotlinx.coroutines.launch

fun LazyListScope.headlineItems(
    headlineArticles: LazyPagingItems<ArticleUi>,
    snackbarHostState: SnackbarHostState,
    onViewMoreClick: () -> Unit,
    onItemClick: (String) -> Unit,
    onFavouriteHeadlineChange: (ArticleUi) -> Unit
) {
    val loadState = headlineArticles.loadState.refresh

    item {
        HeaderTitle(
            title = "Hot News",
            icon = Icons.Default.LocalFireDepartment
        )
        Spacer(
            modifier = Modifier.size(NewsyTheme.dimens.itemSpacing)
        )
    }

    item {
        val scope = rememberCoroutineScope()
        PaginationLoadingItem(
            loadState = loadState,
            items = headlineArticles.itemSnapshotList.items,
            onError = {
                scope.launch {
                    Log.e("Error", it.message ?: "Unknown error")
                    snackbarHostState.showSnackbar(it.message ?: "Unknown error")
                }
            },
            onLoading = {
               HeadlineItemsPlaceholder()
            }
        )
    }

    item {
        if (loadState is LoadState.NotLoading && headlineArticles.itemSnapshotList.items.isNotEmpty()) {
            HeadlineItem(
                articles = headlineArticles.itemSnapshotList.items,
                onCardClick = {
                    onItemClick(it.url)
                },
                onFavouriteChange = onFavouriteHeadlineChange,
                modifier = Modifier,
            )
        }

        Box(
            modifier = Modifier
                .padding(end = NewsyTheme.dimens.mediumPadding)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        onViewMoreClick()
                    }
                    .padding(
                        vertical = NewsyTheme.dimens.itemPadding,
                        horizontal = NewsyTheme.dimens.defaultPadding
                    ),
                text = "View More",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}