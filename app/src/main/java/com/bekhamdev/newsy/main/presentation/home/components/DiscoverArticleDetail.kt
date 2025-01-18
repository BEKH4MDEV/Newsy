package com.bekhamdev.newsy.main.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.bekhamdev.newsy.core.presentation.utils.formatPublishedAtDate
import com.bekhamdev.newsy.main.presentation.model.ArticleUi
import com.bekhamdev.newsy.ui.theme.NewsyTheme

@Composable
fun DiscoverArticleDetail(
    modifier: Modifier = Modifier,
    article: ArticleUi,
    onFavouriteChange: (ArticleUi) -> Unit
) {
    val favouriteIcon = if (article.favourite)
        Icons.Default.BookmarkAdded
    else
        Icons.Default.Bookmark

    val publishedAt = remember(article.publishedAt) {
        formatPublishedAtDate(article.publishedAt)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(
                    horizontal = NewsyTheme.dimens.itemPadding
                ),
            text = article.title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            fontWeight = FontWeight.Bold,

        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(NewsyTheme.dimens.itemPadding)
            ) {
                Text(
                    text = article.sourceName,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = publishedAt,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            IconButton(
                onClick = { onFavouriteChange(article) }
            ) {
                Icon(
                    imageVector = favouriteIcon,
                    contentDescription = "favourite"
                )
            }
        }
    }
}