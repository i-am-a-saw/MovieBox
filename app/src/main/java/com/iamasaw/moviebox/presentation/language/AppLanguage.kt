package com.iamasaw.moviebox.presentation.language

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.iamasaw.moviebox.R
import java.util.Locale

enum class AppLanguage(
    val tag: String,
    @param:StringRes val labelRes: Int,
    val tmdbCode: String,
) {
    RUSSIAN("ru", R.string.russian, "ru-RU"),
    ENGLISH("en", R.string.english, "en-US");

    fun apply() = AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(tag))

    companion object {
        fun current(): AppLanguage {
            val locale = AppCompatDelegate.getApplicationLocales()[0] ?: Locale.getDefault()

            return entries.firstOrNull { it.tag == locale.language } ?: RUSSIAN
        }
    }
}
