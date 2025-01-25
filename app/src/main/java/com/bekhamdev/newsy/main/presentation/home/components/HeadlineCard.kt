package com.bekhamdev.newsy.main.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bekhamdev.newsy.R
import com.bekhamdev.newsy.core.presentation.utils.formatPublishedAtDate
import com.bekhamdev.newsy.main.presentation.model.ArticleUi
import com.bekhamdev.newsy.ui.theme.NewsyTheme

@Composable
fun HeadlineCard(
    modifier: Modifier = Modifier,
    article: ArticleUi,
    onCardClick: (ArticleUi) -> Unit,
    onFavouriteChange: (ArticleUi) -> Unit
) {
    val imgRequest = ImageRequest.Builder(LocalContext.current)
        .data(article.urlToImage)
        .crossfade(true)
        .build()

    val favouriteIcon = if (article.favourite)
        Icons.Default.BookmarkAdded
    else
        Icons.Default.Bookmark

    val publishedAt = remember(article.publishedAt) {
        formatPublishedAtDate(article.publishedAt)
    }

    Card(
        modifier = modifier
            .height(260.dp),
        onClick = { onCardClick(article) }
    ) {
        Column {
            AsyncImage(
                model = imgRequest,
                placeholder = painterResource(R.drawable.ideogram_2_),
                error = painterResource(R.drawable.ideogram_2_),
                contentScale = ContentScale.Crop,
                contentDescription = "news image",
                modifier = Modifier
                    .height(150.dp)
            )
            Column(
                modifier = Modifier
                    .padding(NewsyTheme.dimens.itemPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    fontWeight = FontWeight.Bold,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = article.sourceName,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.tertiary
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
    }
}