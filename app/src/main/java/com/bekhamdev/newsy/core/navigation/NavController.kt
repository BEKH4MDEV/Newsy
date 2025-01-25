package com.bekhamdev.newsy.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bekhamdev.newsy.main.presentation.detail.DetailScreen
import com.bekhamdev.newsy.main.presentation.home.HomeAction
import com.bekhamdev.newsy.main.presentation.home.HomeScreen
import com.bekhamdev.newsy.main.presentation.home.HomeViewModel

@Composable
fun NavController(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val state by viewModel.state.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = Route.Home,
        modifier = modifier
    ) {
        composable<Route.Home> {
            HomeScreen(
                state = state,
                onViewMoreClick = {
                    navController.navigate(Route.Headline)
                },
                openDrawer = {},
                onAction = {
                    viewModel.onAction(it)
                    when (it) {
                        is HomeAction.OnArticleClick -> {
                            navController.navigate(Route.Detail)
                        }
                        else -> {}
                    }
                },
                onSearchClick = {
                    navController.navigate(Route.Search)
                },
            )
        }

        composable<Route.Detail> {

            DetailScreen(
                article = state.articleSelected,
                onAction = {
                    viewModel.onAction(it)
                },
                onSearchClick = {
                    navController.navigate(Route.Search)
                },
                goBack = {
                    navController.popBackStack()
                }
            )

        }

        composable<Route.Headline> {

        }

        composable<Route.Search> {

        }
    }

}