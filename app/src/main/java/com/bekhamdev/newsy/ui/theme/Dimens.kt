package com.bekhamdev.newsy.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens (
    val itemSpacing: Dp = 6.dp,
    val defaultSpacing: Dp = 12.dp,
    val defaultPadding: Dp = 12.dp,
    val itemPadding: Dp = 6.dp,
    val mediumSpacing: Dp = 8.dp,
    val mediumPadding: Dp = 8.dp,
)

val defaultDimens = Dimens()