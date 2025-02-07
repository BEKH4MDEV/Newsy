package com.bekhamdev.newsy.main.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bekhamdev.newsy.core.navigation.Route
import com.bekhamdev.newsy.ui.theme.NewsyTheme

@Composable
fun DrawerContent(
    modifier: Modifier = Modifier,
    currentRoute: Route?,
    closeDrawer: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToFavourites: () -> Unit
) {
    ModalDrawerSheet {
        Column(
            modifier = modifier
                .padding(
                    horizontal = NewsyTheme.dimens.defaultSpacing
                )
        ) {
            NewsyLogo(
                modifier = Modifier
                    .padding(
                        vertical = 24.dp
                    )
            )

            NavigationDrawerItem(
                label = {
                    Text(
                        text = "Home"
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null
                    )
                },
                selected = currentRoute is Route.Home,
                onClick = {
                    if (currentRoute !is Route.Home) {
                        navigateToHome()
                    }
                    closeDrawer()
                },
                modifier = Modifier
                    .padding(
                        bottom = NewsyTheme.dimens.itemPadding
                    )
            )

            NavigationDrawerItem(
                label = {
                    Text(
                        text = "Favourites"
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = null
                    )
                },
                selected = currentRoute is Route.Favourites,
                onClick = {
                    if (currentRoute !is Route.Favourites) {
                        navigateToFavourites()
                    }
                    closeDrawer()
                },
                modifier = Modifier
                    .padding(
                        bottom = NewsyTheme.dimens.itemPadding
                    )
            )

            NavigationDrawerItem(
                label = {
                    Text(
                        text = "Settings"
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null
                    )
                },
                selected = currentRoute is Route.Settings,
                onClick = {
                    if (currentRoute !is Route.Settings) {
                        navigateToSettings()
                    }
                    closeDrawer()
                },
                modifier = Modifier
                    .padding(
                        bottom = NewsyTheme.dimens.itemPadding
                    )
            )
        }
    }
}