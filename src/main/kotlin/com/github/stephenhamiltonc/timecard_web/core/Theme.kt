package com.github.stephenhamiltonc.timecard_web.core

import kotlinx.browser.localStorage
import kotlinx.browser.window

private const val THEME_STORAGE_KEY = "theme"

object Theme {
    val currentTheme: Themes
    private val defaultTheme = Themes.LIGHT

    init {
        val savedTheme = localStorage.getItem(THEME_STORAGE_KEY) ?: ""
        val theme = try { Themes.valueOf(savedTheme) } catch(iae: Exception) { defaultTheme }
        currentTheme = theme
    }

    fun switch(theme: Themes) {
        localStorage.setItem(THEME_STORAGE_KEY, theme.name)
        window.location.reload()
    }
}
