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
val tabletDimens = Dimens(
    itemSpacing = 8.dp,
    defaultSpacing = 14.dp,
    defaultPadding = 18.dp,
    itemPadding = 8.dp,
    mediumSpacing = 10.dp,
    mediumPadding = 14.dp
)