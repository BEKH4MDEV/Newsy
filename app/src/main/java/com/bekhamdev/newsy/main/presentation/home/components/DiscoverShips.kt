package com.bekhamdev.newsy.main.presentation.home.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bekhamdev.newsy.core.domain.utils.ArticleCategory
import com.bekhamdev.newsy.core.domain.utils.SharedValues

@Composable
fun DiscoverShips(
    modifier: Modifier = Modifier,
    selectCategory: ArticleCategory,
    categories: List<ArticleCategory>,
    onCategoryChange: (ArticleCategory) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.Center,
    ) {
        categories.forEachIndexed { index, category ->
            if (category.category != (SharedValues.HEADLINE_CATEGORY?.category
                    ?: ArticleCategory.GENERAL.category)
            ) {
                DiscoverShip(
                    selected = category == selectCategory,
                    onClick = {
                        onCategoryChange(category)
                    },
                    label = category.name,
                    first = index == 0,
                    last = index == categories.size - 1
                )
            }
        }
    }
}