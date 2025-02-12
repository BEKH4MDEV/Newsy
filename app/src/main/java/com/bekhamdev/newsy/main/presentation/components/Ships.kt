package com.bekhamdev.newsy.main.presentation.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bekhamdev.newsy.core.domain.utils.ArticleCategory
import com.bekhamdev.newsy.core.domain.utils.SharedValues

@Composable
fun Ships(
    modifier: Modifier = Modifier,
    selectCategory: ArticleCategory,
    categories: List<ArticleCategory>,
    onCategoryChange: (ArticleCategory) -> Unit,
    includeAll: Boolean = false,
    scrollState: ScrollState = rememberScrollState()
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.Center,
    ) {
        categories.forEachIndexed { index, category ->
            if (includeAll || (category.category != (SharedValues.HEADLINE_CATEGORY?.category
                    ?: ArticleCategory.GENERAL.category))
            ) {
                Ship(
                    selected = category == selectCategory,
                    onClick = {
                        onCategoryChange(category)
                    },
                    label = category.name,
                )
                if (index != categories.lastIndex - (if (includeAll) 0 else 1)) {
                    Spacer(modifier = Modifier.width(12.dp))
                }
            }
        }
    }
}