package com.bekhamdev.newsy.main.presentation.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bekhamdev.newsy.R
import com.bekhamdev.newsy.main.presentation.home.components.DiscoverArticleDetail
import com.bekhamdev.newsy.main.presentation.model.ArticleUi
import com.bekhamdev.newsy.ui.theme.NewsyTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ArticleItem(
    modifier: Modifier = Modifier,
    article: ArticleUi,
    onClick: (ArticleUi) -> Unit,
    onFavouriteChange: (ArticleUi) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Card(
        modifier = modifier
            .padding(
                vertical = NewsyTheme.dimens.itemPadding
            )
            .clickable {
                onClick(article)
            }
    ) {
        with(sharedTransitionScope) {
            Row(
                modifier = Modifier
                    .height(115.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = article.urlToImage,
                    placeholder = painterResource(R.drawable.news_place_holder),
                    error = painterResource(R.drawable.news_place_holder),
                    contentDescription = "Article Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(150.dp)
                        .sharedElement(
                            state = rememberSharedContentState(key = "image-${article.url}-${article.category}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .clip(CardDefaults.shape)
                )
                DiscoverArticleDetail(
                    article = article,
                    onFavouriteChange = onFavouriteChange
                )
            }
        }

    }
}