package com.bekhamdev.newsy.core.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.bekhamdev.newsy.core.presentation.utils.calculateLandscapePadding
import com.bekhamdev.newsy.main.presentation.GlobalAction
import com.bekhamdev.newsy.main.presentation.GlobalViewModel
import com.bekhamdev.newsy.main.presentation.components.DrawerContent
import com.bekhamdev.newsy.main.presentation.detail.DetailScreen
import com.bekhamdev.newsy.main.presentation.favorites.FavoritesScreen
import com.bekhamdev.newsy.main.presentation.favorites.FavoritesViewModel
import com.bekhamdev.newsy.main.presentation.headline.HeadlineScreen
import com.bekhamdev.newsy.main.presentation.home.HomeScreen
import com.bekhamdev.newsy.main.presentation.home.HomeViewModel
import com.bekhamdev.newsy.main.presentation.search.SearchScreen
import com.bekhamdev.newsy.main.presentation.search.SearchViewModel
import com.bekhamdev.newsy.main.presentation.settings.SettingsScreen
import kotlinx.coroutines.launch

@Composable
fun NavController(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    globalViewModel: GlobalViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val homeState by homeViewModel.state.collectAsStateWithLifecycle()
    val globalState by globalViewModel.state.collectAsStateWithLifecycle()
    val headlineArticles = homeState.headlineArticles.collectAsLazyPagingItems()
    val scope = rememberCoroutineScope()
    val landscapePadding = calculateLandscapePadding()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                currentRoute = Route.Home,
                closeDrawer = {
                    scope.launch {
                        drawerState.close()
                    }
                },
                navigateToHome = {
                    navController.navigate(Route.Home)
                },
                navigateToSettings = {
                    navController.navigate(Route.Settings)
                },
                navigateToFavourites = {
                    navController.navigate(Route.Favourites)
                },
                modifier = Modifier
                    .padding(
                        landscapePadding
                    )
            )
        },
        gesturesEnabled = drawerState.isClosed.not()
    ) {
        NavHost(
            navController = navController,
            startDestination = Route.Home,
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(
                    landscapePadding
                ),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(500)
                ) + fadeIn(animationSpec = tween(500))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(500)
                ) + fadeOut(animationSpec = tween(500))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(500)
                ) + fadeIn(animationSpec = tween(500))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(500)
                ) + fadeOut(animationSpec = tween(500))
            }
        ) {
            composable<Route.Home> {
                val discoverArticles = homeState.discoverArticles.collectAsLazyPagingItems()
                HomeScreen(
                    state = homeState,
                    onViewMoreClick = {
                        navController.navigate(Route.Headline)
                    },
                    openDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
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

            composable<Route.Favourites> {
                val favouritesViewModel: FavoritesViewModel = hiltViewModel()
                val favouritesState by favouritesViewModel.state.collectAsStateWithLifecycle()
                FavoritesScreen(
                    goBack = {
                        navController.popBackStack()
                    },
                    isLoading = favouritesState.isLoading,
                    onFavouritesAction = favouritesViewModel::onAction,
                    onGlobalAction = {
                        globalViewModel.onAction(it)
                        when (it) {
                            is GlobalAction.OnArticleClick -> {
                                navController.navigate(Route.Detail)
                            }
                            else -> {}
                        }
                    },
                    favourites = favouritesState.favorites,
                    categories = favouritesState.categories,
                    selectedCategory = favouritesState.selectedCategory
                )
            }

            composable<Route.Settings> {
                SettingsScreen(
                    goBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}