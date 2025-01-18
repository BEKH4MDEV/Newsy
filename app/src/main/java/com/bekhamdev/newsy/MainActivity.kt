package com.bekhamdev.newsy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
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
                HomeScreen(
                    onViewMoreClick = {},
                    onItemClick = {},
                    onSearchClick = {},
                    openDrawer = {}
                )
            }
        }
    }
}