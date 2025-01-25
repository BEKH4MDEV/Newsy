package com.bekhamdev.newsy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import com.bekhamdev.newsy.core.navigation.NavController
import com.bekhamdev.newsy.core.presentation.utils.calculateLandscapePadding
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
                val paddingValues = calculateLandscapePadding()
                NavController(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .padding(paddingValues)
                )
            }
        }
    }
}