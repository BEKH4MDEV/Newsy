package com.bekhamdev.newsy.main.presentation.detail

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
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bekhamdev.newsy.R
import com.bekhamdev.newsy.core.presentation.utils.ArticleType
import com.bekhamdev.newsy.core.presentation.utils.formatPublishedAtDate
import com.bekhamdev.newsy.main.presentation.components.TopBar
import com.bekhamdev.newsy.main.presentation.detail.components.InformationDetail
import com.bekhamdev.newsy.main.presentation.home.HomeAction
import com.bekhamdev.newsy.main.presentation.model.ArticleInformation
import com.bekhamdev.newsy.main.presentation.model.ArticleUi
import com.bekhamdev.newsy.ui.theme.NewsyTheme

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    articleInformation: ArticleInformation?,
    onSearchClick: () -> Unit = {},
    goBack: () -> Unit = {},
    onAction: (HomeAction) -> Unit = {}
) {
    val article = articleInformation?.article
    if (article == null) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        val imgRequest = ImageRequest.Builder(LocalContext.current)
            .data(article.urlToImage)
            .crossfade(true)
            .build()

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
                            maxLines = 1
                        )
                    },
                    onSearchClick = onSearchClick,
                    goBack = goBack
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
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    AsyncImage(
                        model = imgRequest,
                        placeholder = painterResource(R.drawable.ideogram_2_),
                        error = painterResource(R.drawable.ideogram_2_),
                        contentScale = ContentScale.Crop,
                        contentDescription = "news image",
                        modifier = Modifier
                            .height(260.dp)
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
                            onAction(
                                if (articleInformation.type == ArticleType.HEADLINE) {
                                    HomeAction.OnHeadlineFavouriteChange(article)
                                } else {
                                    HomeAction.OnDiscoverFavouriteChange(article)
                                }
                            )
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

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DetailScreenPreview() {
    val article = ArticleUi(
        author = "John Doe",
        content = """
        This is the detailed content of the article. 
        It provides an in-depth analysis of the latest technological trends, 
        including expert insights, market projections, and real-world applications. 
        The content aims to educate and engage readers by exploring these topics comprehensively, 
        offering actionable insights and a deeper understanding of the rapidly evolving tech landscape.
    """.trimIndent(),
        description = """
        An overview of the latest technological trends, highlighting key developments, 
        market shifts, and their potential impact on industries worldwide.
    """.trimIndent(),
        publishedAt = "2025-01-23T10:00:00Z",
        sourceName = "Tech News Daily",
        title = "Emerging Trends in Technology for 2025",
        url = "https://example.com/article",
        urlToImage = "https://example.com/technology-trends.jpg",
        favourite = true,
        category = "Technology"
    )

    NewsyTheme {
        DetailScreen(
            articleInformation = ArticleInformation(
                article = article,
                type = ArticleType.DISCOVER
            )
        )
    }
}