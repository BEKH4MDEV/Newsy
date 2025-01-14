package com.bekhamdev.newsy.main.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.bekhamdev.newsy.main.presentation.model.ArticleUi
import com.bekhamdev.newsy.ui.theme.NewsyTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun HeadlineItem(
    articles: List<ArticleUi>,
    modifier: Modifier = Modifier,
    onCardClick: (ArticleUi) -> Unit,
    onViewMoreClick: () -> Unit,
    onFavouriteChange: (ArticleUi) -> Unit
) {
    val articlesSize = remember(articles) {
        articles.size
    }
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { articlesSize }
    )
    val scope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(pagerState.currentPage, articlesSize) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                delay(6000L)
                val nextPage = if (pagerState.currentPage + 1 < articlesSize) {
                    pagerState.currentPage + 1
                } else {
                    0
                }
                scope.launch {
                    if (!pagerState.isScrollInProgress) {
                        pagerState.animateScrollToPage(nextPage)
                    }
                }
            }
        }
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(NewsyTheme.dimens.defaultPadding),
            beyondViewportPageCount = 1,
            pageSize = PageSize.Fill,
            pageSpacing = NewsyTheme.dimens.itemSpacing,
        ) { page ->
            HeadlineCard(
                article = articles[page],
                onCardClick = onCardClick,
                onFavouriteChange = onFavouriteChange
            )
        }
        Box(
            modifier = Modifier
                .padding(end = NewsyTheme.dimens.mediumPadding)
                .align(Alignment.End)
        ) {

            if (articlesSize > 0) {
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
}

@Composable
@Preview(showBackground = true)
fun HeadlineItemPreview() {
    val articles = listOf(
        ArticleUi(
            id = 1L,
            author = "Jane Doe",
            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            description = "An overview of Kotlin features.",
            publishedAt = "2025-01-01T10:00:00Z",
            sourceName = "Tech News",
            title = "Exploring Kotlin in 2025",
            url = "https://technews.com/articles/kotlin-2025",
            urlToImage = "https://technews.com/images/kotlin.jpg",
            favourite = true,
            category = "Programming",
            page = 1
        ),
        ArticleUi(
            id = 2L,
            author = "John Smith",
            content = "Nullam non urna eu lectus fermentum bibendum.",
            description = "Why Kotlin is a great choice for Android development.",
            publishedAt = "2025-01-10T15:00:00Z",
            sourceName = "Dev Blog",
            title = "Kotlin: The Android Developer's Best Friend",
            url = "https://devblog.com/articles/kotlin-android",
            urlToImage = null,
            favourite = false,
            category = "Android Development",
            page = 2
        )
    )

    NewsyTheme {
        HeadlineItem(
            articles = articles,
            onCardClick = {},
            onViewMoreClick = {},
            onFavouriteChange = {},
        )
    }
}