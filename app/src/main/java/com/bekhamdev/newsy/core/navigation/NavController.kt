package com.bekhamdev.newsy.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.bekhamdev.newsy.main.presentation.GlobalAction
import com.bekhamdev.newsy.main.presentation.GlobalViewModel
import com.bekhamdev.newsy.main.presentation.detail.DetailScreen
import com.bekhamdev.newsy.main.presentation.headline.HeadlineScreen
import com.bekhamdev.newsy.main.presentation.home.HomeScreen
import com.bekhamdev.newsy.main.presentation.home.HomeViewModel
import com.bekhamdev.newsy.main.presentation.search.SearchScreen
import com.bekhamdev.newsy.main.presentation.search.SearchViewModel

@Composable
fun NavController(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    globalViewModel: GlobalViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    val homeState by homeViewModel.state.collectAsStateWithLifecycle()
    val globalState by globalViewModel.state.collectAsStateWithLifecycle()
    val headlineArticles = homeState.headlineArticles.collectAsLazyPagingItems()

    NavHost(
        navController = navController,
        startDestination = Route.Home,
        modifier = modifier
    ) {
        composable<Route.Home> {
            val discoverArticles = homeState.discoverArticles.collectAsLazyPagingItems()
            HomeScreen(
                state = homeState,
                onViewMoreClick = {
                    navController.navigate(Route.Headline)
                },
                openDrawer = {},
                onHomeAction = homeViewModel::onAction,
                onGlobalAction = {
                    globalViewModel.onAction(it)
                    when (it) {
                        is GlobalAction.OnArticleClick -> {
                            navController.navigate(Route.Detail)
                        }

                        else -> {}
                    }
                },
                onSearchClick = {
                    navController.navigate(Route.Search)
                },
                headlineArticles = headlineArticles,
                discoverArticles = discoverArticles
            )
        }

        composable<Route.Detail> {
            DetailScreen(
                article = globalState.articleSelected,
                onFavoriteChange = {
                    globalViewModel.onAction(GlobalAction.OnFavoriteChange(it))
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
            HeadlineScreen(
                articles = headlineArticles,
                onSearchClick = {
                    navController.navigate(Route.Search)
                },
                goBack = {
                    navController.popBackStack()
                },
                onGlobalAction = {
                    globalViewModel.onAction(it)
                    when (it) {
                        is GlobalAction.OnArticleClick -> {
                            navController.navigate(Route.Detail)
                        }

                        else -> {}
                    }
                }
            )
        }

        composable<Route.Search> {
            val searchViewModel: SearchViewModel = hiltViewModel()
            val searchState by searchViewModel.state.collectAsStateWithLifecycle()
            val searchArticles = searchState.searchArticles.collectAsLazyPagingItems()
            SearchScreen(
                articles = searchArticles,
                onSearchAction = searchViewModel::onAction,
                onGlobalAction = {
                    globalViewModel.onAction(it)
                    when (it) {
                        is GlobalAction.OnArticleClick -> {
                            navController.navigate(Route.Detail)
                        }
                        else -> {}
                    }
                },
                goBack = {
                    navController.popBackStack()
                }
            )
        }
    }

}