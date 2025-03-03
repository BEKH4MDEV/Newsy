package com.bekhamdev.newsy.core.presentation.utils

import android.view.Surface
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun calculateLandscapePadding(): PaddingValues {
    val windowInsets = WindowInsets.displayCutout
    val insetsPadding = windowInsets.asPaddingValues()

    val rotation = LocalView.current.display?.rotation ?: Surface.ROTATION_0

    return when (rotation) {
        Surface.ROTATION_90 ->
            PaddingValues(
                top = 0.dp,
                bottom = 0.dp,
                start = insetsPadding.calculateLeftPadding(LayoutDirection.Ltr),
                end = 0.dp
            )

        Surface.ROTATION_270 ->
            PaddingValues(
                top = 0.dp,
                bottom = 0.dp,
                start = 0.dp,
                end = insetsPadding.calculateRightPadding(LayoutDirection.Ltr)
            )


        else -> PaddingValues(0.dp)
    }
}
