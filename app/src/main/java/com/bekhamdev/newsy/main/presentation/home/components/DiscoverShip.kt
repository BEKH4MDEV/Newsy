package com.bekhamdev.newsy.main.presentation.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bekhamdev.newsy.ui.theme.NewsyTheme

@Composable
fun DiscoverShip(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    first: Boolean = false,
    last: Boolean = false
) {
    InputChip(
        modifier = modifier
            .padding(horizontal = if (first || last) 0.dp else NewsyTheme.dimens.itemPadding)
            .padding(
                end = if (first) NewsyTheme.dimens.itemPadding else 0.dp,
                start = if (last) NewsyTheme.dimens.itemPadding else 0.dp
            ),
        selected = selected,
        onClick = {
            if (!selected) onClick()
        },
        label = { Text(text = label) },
    )
}