package com.bekhamdev.newsy.main.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bekhamdev.newsy.ui.theme.NewsyTheme

@Composable
fun Ship(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    label: String
) {
    InputChip(
        modifier = modifier,
        selected = selected,
        onClick = {
            if (!selected) onClick()
        },
        label = { Text(text = label) },
    )
}