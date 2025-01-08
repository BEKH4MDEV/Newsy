package com.bekhamdev.newsy.core.presentation.utils

data class Language(
    val code: String,
    val name: String
)

val languageCodeList = listOf(
    Language("en", "English"),
    Language("ar", "Arabic"),
    Language("de", "German"),
    Language("es", "Spanish"),
    Language("fr", "French"),
    Language("tr", "Turkish"),
)
