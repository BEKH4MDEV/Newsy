package com.bekhamdev.newsy.main.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.paging.compose.LazyPagingItems
import com.bekhamdev.newsy.main.presentation.components.HeaderTitle
import com.bekhamdev.newsy.main.presentation.model.ArticleUi
import com.bekhamdev.newsy.ui.theme.NewsyTheme

fun LazyListScope.headlineItems(
    headlineArticles: LazyPagingItems<ArticleUi>,
    onViewMoreClick: () -> Unit,
    onItemClick: (ArticleUi) -> Unit,
    onFavouriteHeadlineChange: (ArticleUi) -> Unit,
    listState: LazyListState
) {
    item(key = "Headline Header") {
        HeaderTitle(
            title = "Hot News",
            icon = Icons.Default.LocalFireDepartment,
            modifier = Modifier
                .padding(
                    top = NewsyTheme.dimens.defaultPadding,
                )
        )
        Spacer(
            modifier = Modifier.size(NewsyTheme.dimens.itemSpacing)
        )
    }

    item(key = "Headline Placeholder") {
        when {
            headlineArticles.itemCount == 0 -> {
                HeadlineItemsPlaceholder()
            }
        }
    }

    item(key = "Headline Pager") {
        if (headlineArticles.itemCount > 0) {
            HeadlineItem(
                articles = headlineArticles.itemSnapshotList.items,
                onCardClick = onItemClick,
                onFavouriteChange = onFavouriteHeadlineChange,
                modifier = Modifier,
                listState = listState
            )
        }

        Box(
            modifier = Modifier
                .padding(end = NewsyTheme.dimens.defaultPadding)
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