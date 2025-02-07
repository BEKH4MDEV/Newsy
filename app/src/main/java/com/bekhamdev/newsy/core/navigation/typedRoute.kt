package com.bekhamdev.newsy.core.navigation

fun String.toTypedRoute(): Route? {
    return when {
        this.endsWith("Route.Home") -> Route.Home
        this.endsWith("Route.Detail") -> Route.Detail
        this.endsWith("Route.Headline") -> Route.Headline
        this.endsWith("Route.Settings") -> Route.Settings
        this.endsWith("Route.Favourites") -> Route.Favourites
        this.endsWith("Route.Search") -> Route.Search
        else -> null
    }
}