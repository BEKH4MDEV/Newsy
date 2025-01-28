package com.bekhamdev.newsy.core.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.bekhamdev.newsy.core.presentation.utils.HandlePagingErrors
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
    val headlineArticles = state.headlineArticles.collectAsLazyPagingItems()
    val discoverArticles = state.discoverArticles.collectAsLazyPagingItems()
    val loadStateHeadline = headlineArticles.loadState.mediator
    val loadStateDiscover = discoverArticles.loadState.mediator
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    HandlePagingErrors(
        loadStates = listOf(
            loadStateDiscover,
            loadStateHeadline
        ),
        snackbarHostState = snackBarHostState
    )

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
                headlineArticles = headlineArticles,
                discoverArticles = discoverArticles,
                snackbarHostState = snackBarHostState
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