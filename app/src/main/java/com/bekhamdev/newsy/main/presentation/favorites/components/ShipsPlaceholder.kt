package com.bekhamdev.newsy.main.presentation.favorites.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bekhamdev.newsy.core.domain.utils.ArticleCategory
import com.bekhamdev.newsy.ui.theme.NewsyTheme

@Composable
fun ShipsPlaceholder(
    modifier: Modifier = Modifier
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val shipWidth = 80.dp
    val shipsCount = ((screenWidth / shipWidth).toInt()).coerceAtMost(
        ArticleCategory.entries.size
    )

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(shipsCount) {
            Card(
                modifier = Modifier
                    .height(30.dp)
                    .width(shipWidth)
            ) {}
            if (it != shipsCount - 1) {
                Spacer(modifier = Modifier.width(12.dp))
            }
        }
    }
}
