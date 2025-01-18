package com.bekhamdev.newsy.main.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bekhamdev.newsy.R
import com.bekhamdev.newsy.main.presentation.model.ArticleUi
import com.bekhamdev.newsy.ui.theme.NewsyTheme

@Composable
fun DiscoverArticleItem(
    modifier: Modifier = Modifier,
    article: ArticleUi,
    onClick: (ArticleUi) -> Unit,
    onFavouriteChange: (ArticleUi) -> Unit
) {
    val imgRequest = ImageRequest
        .Builder(LocalContext.current)
        .data(article.urlToImage)
        .crossfade(true)
        .build()

    Card(
        modifier = modifier
            .padding(
                vertical = NewsyTheme.dimens.itemPadding
            )
            .clickable {
                onClick(article)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imgRequest,
                placeholder = painterResource(R.drawable.news_place_holder),
                error = painterResource(R.drawable.news_place_holder),
                contentDescription = "Article Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(150.dp)
                    .height(100.dp)
            )
            DiscoverArticleDetail(
                article = article,
                onFavouriteChange = onFavouriteChange
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DiscoverArticleItemPreview() {
    NewsyTheme {
        DiscoverArticleItem(
            article = ArticleUi(
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
            ),
            onClick = {},
            onFavouriteChange = {}
        )
    }
}