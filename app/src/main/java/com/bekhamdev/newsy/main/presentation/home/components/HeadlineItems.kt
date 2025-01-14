package com.bekhamdev.newsy.main.presentation.home.components

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.bekhamdev.newsy.main.presentation.model.ArticleUi
import com.bekhamdev.newsy.ui.theme.NewsyTheme
import kotlinx.coroutines.launch

fun LazyListScope.headlineItems(
    headlineArticles: LazyPagingItems<ArticleUi>,
    snackbarHostState: SnackbarHostState,
    onViewMoreClick: () -> Unit,
    onHeadlineItemClick: (Long) -> Unit,
    onFavouriteHeadlineChange: (ArticleUi) -> Unit
) {
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
            loadState = headlineArticles.loadState.mediator?.refresh,
            onError = {
                scope.launch {
                    Log.e("Error", it.message ?: "Unknown error")
                    snackbarHostState.showSnackbar(it.message ?: "Unknown error")
                }
            },
            onLoading = {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(top = NewsyTheme.dimens.defaultPadding)
                        .fillMaxWidth()
                        .wrapContentSize(align = Alignment.Center)
                )
            }
        )
    }

    item {
        val articleList = headlineArticles.itemSnapshotList.items
        HeadlineItem(
            articles = articleList,
            onCardClick = {
                onHeadlineItemClick(it.id)
            },
            onViewMoreClick = onViewMoreClick,
            onFavouriteChange = onFavouriteHeadlineChange
        )
    }
}