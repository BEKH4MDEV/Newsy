package com.bekhamdev.newsy.main.presentation.detail

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bekhamdev.newsy.R
import com.bekhamdev.newsy.core.presentation.utils.formatPublishedAtDate
import com.bekhamdev.newsy.main.presentation.components.TopBar
import com.bekhamdev.newsy.main.presentation.detail.components.InformationDetail
import com.bekhamdev.newsy.main.presentation.model.ArticleUi
import com.bekhamdev.newsy.ui.theme.NewsyTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    article: ArticleUi?,
    goBack: () -> Unit = {},
    onFavoriteChange: (ArticleUi) -> Unit = {},
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    if (article == null) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {}
    } else {
        val favouriteIcon = if (article.favourite)
            Icons.Default.BookmarkAdded
        else
            Icons.Default.Bookmark

        val publishedAt = remember(article) {
            formatPublishedAtDate(
                article.publishedAt,
                includeYear = true
            )
        }

        Scaffold(
            modifier = modifier
                .fillMaxSize(),
            topBar = {
                TopBar(
                    title = {
                        Text(
                            text = article.sourceName,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    goBack = goBack,
                    isSearchVisible = false
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .padding(
                        horizontal = NewsyTheme.dimens.defaultPadding
                    )
            ) {
                with(sharedTransitionScope) {
                    AsyncImage(
                        model = article.urlToImage,
                        placeholder = painterResource(R.drawable.ideogram_2_),
                        error = painterResource(R.drawable.ideogram_2_),
                        contentScale = ContentScale.Crop,
                        contentDescription = "news image",
                        modifier = Modifier
                            .height(260.dp)
                            .sharedElement(
                                state = rememberSharedContentState(key = "image-${article.url}-${article.category}"),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                            .clip(CardDefaults.shape)
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(vertical = NewsyTheme.dimens.itemPadding)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {
                            onFavoriteChange(article)
                        }
                    ) {
                        Icon(
                            imageVector = favouriteIcon,
                            contentDescription = "favourite"
                        )
                    }
                    Text(
                        modifier = Modifier
                            .padding(end = NewsyTheme.dimens.defaultPadding),
                        text = publishedAt,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Gray
                    )
                }

                InformationDetail(
                    title = article.title,
                    description = article.description,
                    content = article.content,
                    author = article.author,
                    url = article.url
                )
            }
        }
    }
}