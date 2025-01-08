package com.bekhamdev.newsy.core.presentation.utils

import androidx.annotation.DrawableRes
import com.bekhamdev.newsy.R

data class Country(
    val code: String,
    val name: String,
    @DrawableRes val icRedId: Int
)

val countryCodeList = listOf(
    Country("us", "United States", R.drawable.ic_united_states),
    Country("za", "South Africa", R.drawable.ic_south_africa),
    Country("jp", "Japan", R.drawable.ic_japan),
    Country("de", "Germany", R.drawable.ic_germany),
    Country("cn", "China", R.drawable.ic_china),
    Country("ae", "United Arab Emirates", R.drawable.ic_united_arab_emirates),
    Country("in", "India", R.drawable.ic_india),
    Country("br", "Brazil", R.drawable.ic_brazil),
    Country("tr", "Turkey", R.drawable.ic_turkey),
    Country("ua", "Ukraine", R.drawable.ic_ukraine),
    Country("gb", "United Kingdom", R.drawable.ic_uk)
)
