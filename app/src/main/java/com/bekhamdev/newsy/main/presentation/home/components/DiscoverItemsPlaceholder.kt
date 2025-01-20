package com.bekhamdev.newsy.main.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.bekhamdev.newsy.core.domain.utils.SharedValues
import com.bekhamdev.newsy.ui.theme.NewsyTheme

@Composable
fun DiscoverItemsPlaceholder(
    modifier: Modifier = Modifier,
) {

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val count = remember(screenHeight) {
        (screenHeight / 100.dp).minus(1).toInt()
    }

    repeat(count) {
        Card(
            modifier = modifier
                .padding(NewsyTheme.dimens.itemPadding)
                .border(
                    width = 5.dp,
                    color = CardDefaults.cardColors().containerColor,
                    shape = CardDefaults.shape
                )
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .height(100.dp)

            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(150.dp)
                        .background(
                            color = MaterialTheme.colorScheme.background
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
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
                            .height(12.dp)
                            .fillMaxWidth(0.8f)

                    )
                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clip(CardDefaults.shape)
                            .background(color = MaterialTheme.colorScheme.background)
                            .height(12.dp)
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