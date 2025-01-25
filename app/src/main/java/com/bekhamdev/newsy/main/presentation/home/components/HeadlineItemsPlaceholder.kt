package com.bekhamdev.newsy.main.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bekhamdev.newsy.ui.theme.NewsyTheme

@Composable
fun HeadlineItemsPlaceholder(
    modifier: Modifier = Modifier,
    count: Int = 3,
) {

    val items = (0..count).toList()

    val pagerState = rememberPagerState(
        initialPage = 1,
        pageCount = { items.size }
    )

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(NewsyTheme.dimens.defaultPadding),
        pageSize = PageSize.Fill,
        pageSpacing = NewsyTheme.dimens.itemSpacing,
        userScrollEnabled = false,
        modifier = modifier
    ) {

        Card(
            modifier = Modifier
                .border(
                    width = 5.dp,
                    color = CardDefaults.cardColors().containerColor,
                    shape = CardDefaults.shape
                )
                .fillMaxWidth()
                .height(260.dp)
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(
                            color = MaterialTheme.colorScheme.background
                        )
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(
                                    start = 8.dp,
                                    top = 8.dp,
                                    bottom = 6.dp
                                )
                                .clip(CardDefaults.shape)
                                .background(color = MaterialTheme.colorScheme.background)
                                .height(15.dp)
                                .fillMaxWidth(0.8f)
                        )
                        Box(
                            modifier = Modifier
                                .padding(
                                    start = 8.dp
                                )
                                .clip(CardDefaults.shape)
                                .background(color = MaterialTheme.colorScheme.background)
                                .height(15.dp)
                                .fillMaxWidth(0.6f)

                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Box(
                            modifier = Modifier
                                .padding(
                                    start = 8.dp
                                )
                                .clip(CardDefaults.shape)
                                .background(color = MaterialTheme.colorScheme.background)
                                .height(7.dp)
                                .fillMaxWidth(0.2f)

                        )
                        Box(
                            modifier = Modifier
                                .padding(
                                    top = 3.dp,
                                    start = 8.dp,
                                    bottom = 8.dp
                                )
                                .clip(CardDefaults.shape)
                                .background(color = MaterialTheme.colorScheme.background)
                                .height(7.dp)
                                .fillMaxWidth(0.1f)

                        )
                    }

                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeadlineItemsPlaceholderPreview() {
    NewsyTheme {
        HeadlineItemsPlaceholder()
    }
}




