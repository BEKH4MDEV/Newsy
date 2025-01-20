package com.bekhamdev.newsy

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.bekhamdev.newsy.main.presentation.home.HomeScreen
import com.bekhamdev.newsy.ui.theme.NewsyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            val windowWidthSizeClass = windowSizeClass.widthSizeClass
            NewsyTheme(
                windowSize = windowWidthSizeClass
            ) {
                val configuration = LocalConfiguration.current
                val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
                val statusBarInsets = WindowInsets.statusBars.asPaddingValues()

                val startPadding = if (isPortrait) {
                    0.dp
                } else {
                    statusBarInsets.calculateTopPadding()
                }

                HomeScreen(
                    onViewMoreClick = {},
                    onItemClick = {},
                    onSearchClick = {},
                    openDrawer = {},
                    startPadding = startPadding
                )
            }
        }
    }
}