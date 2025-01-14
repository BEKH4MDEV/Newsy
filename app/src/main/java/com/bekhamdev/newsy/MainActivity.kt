package com.bekhamdev.newsy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bekhamdev.newsy.main.presentation.home.HomeScreen
import com.bekhamdev.newsy.ui.theme.NewsyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsyTheme {
                HomeScreen(
                    onViewMoreClick = {},
                    onHeadlineItemClick = {},
                    onSearchClick = {},
                    openDrawer = {}
                )
            }
        }
    }
}