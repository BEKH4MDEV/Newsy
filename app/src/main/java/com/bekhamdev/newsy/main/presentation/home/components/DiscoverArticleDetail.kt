package com.bekhamdev.newsy.main.presentation.home.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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

    val dateColor = if (isSystemInDarkTheme()) {
        Color.LightGray
    } else {
        Color.DarkGray
    }

    Column(
        modifier = modifier
            .padding(NewsyTheme.dimens.itemPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = article.title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,

        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = article.sourceName,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = publishedAt,
                    style = MaterialTheme.typography.bodySmall,
                    color = dateColor
                )
            }

            Box(
                modifier = Modifier
                    .weight(.7f),
                contentAlignment = Alignment.CenterEnd
            ) {
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
}