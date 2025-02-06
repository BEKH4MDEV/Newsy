package com.bekhamdev.newsy.core.presentation.utils

import androidx.annotation.DrawableRes
import com.bekhamdev.newsy.R

data class Country(
    val code: String,
    val name: String,
    val language: String,
    @DrawableRes val icRedId: Int
)

val countryCodeList = listOf(
    Country(
        "us",
        "United States",
        "en",
        R.drawable.ic_united_states
    ),
)
