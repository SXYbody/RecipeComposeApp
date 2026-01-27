package com.yourcompany.recipecomposeapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val PrimaryColor = Color(0xFF5F3678) // Фиолетовый для заголовков и акцентов
val AccentColor = Color(0xFFE13E3E) // Красный для кнопки избранного
val AccentBlue = Color(0xFF525DC0) // Голубой для кнопки категорий и слайдера
val SliderTrackColor = Color(0xFFA1A9F9) // Светло-голубой для дорожки слайдера
val BackgroundColor = Color(0xFFF4FAFF) // Светло-голубой фон приложения
val SurfaceColor = Color(0xFFFFFFFF) // Белый фон карточек
val SurfaceVariantColor = Color(0xFF303030) // Тёмно-серый для неактивных элементов
val DividerColor = Color(0xFFF5F5F5) // Светло-серый для разделителей

val TextPrimaryColor = Color(0xFF000000) // Основной текст
val TextSecondaryColor = Color(0xFF666666) // Второстепенный текст (описания)

private val RecipesAppLightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    error = AccentColor,
    tertiary = AccentBlue,
    tertiaryContainer = SliderTrackColor,
    background = BackgroundColor,
    surface = SurfaceColor,
    outline = DividerColor,
)
val PrimaryColorDark = Color(0xFF9A9EFF) // Фиолетовый для заголовков и акцентов
val AccentColorDark = Color(0xFFE57373) // Красный для кнопки избранного
val AccentBlueDark = Color(0xFF64B5F6) // Голубой для кнопки категорий и слайдера
val SliderTrackColorDark = Color(0xFF7986CB) // Светло-голубой для дорожки слайдера
val BackgroundColorDark = Color(0xFF121212) // Темный фон приложения
val SurfaceColorDark = Color(0xFF1E1E1E) // Темно-серый фон карточек
val SurfaceVariantColorDark = Color(0xFF424242) // Серый для неактивных элементов
val DividerColorDark = Color(0xFF2C2C2C) // Темно-серый для разделителей

val TextPrimaryColorDark = Color(0xFFE6E6E6) // Основной текст
val TextSecondaryColorDark = Color(0xFFB0B0B0) // Второстепенный текст (описания)

private val RecipesAppDarkColorScheme = darkColorScheme(
    primary = PrimaryColorDark,
    error = AccentColorDark,
    tertiary = AccentBlueDark,
    tertiaryContainer = SliderTrackColorDark,
    background = BackgroundColorDark,
    surface = SurfaceColorDark,
    outline = DividerColorDark,
)

@Composable
fun RecipeComposeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> RecipesAppDarkColorScheme
        else -> RecipesAppLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = recipesAppTypography,
        content = content
    )
}