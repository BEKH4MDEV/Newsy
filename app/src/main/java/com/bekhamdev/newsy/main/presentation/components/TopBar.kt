package com.bekhamdev.newsy.main.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    onSearchClick: () -> Unit = {},
    goBack: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = {
            IconButton(
                onClick = goBack
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "go back",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        actions = {
            IconButton(
                onClick = onSearchClick
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "search",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}