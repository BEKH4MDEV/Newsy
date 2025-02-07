package com.bekhamdev.newsy.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Home: Route

    @Serializable
    data object Detail: Route

    @Serializable
    data object Headline: Route

    @Serializable
    data object Settings: Route

    @Serializable
    data object Favourites: Route

    @Serializable
    data object Search: Route
}