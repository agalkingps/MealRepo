package ru.agalkingps.mealapp.order_flow.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Material 3 color schemes
private val mealDarkColorScheme = darkColorScheme(
    primary = mealDarkPrimary,
    onPrimary = mealDarkOnPrimary,
    primaryContainer = mealDarkPrimaryContainer,
    onPrimaryContainer = mealDarkOnPrimaryContainer,
    inversePrimary = mealDarkPrimaryInverse,
    secondary = mealDarkSecondary,
    onSecondary = mealDarkOnSecondary,
    secondaryContainer = mealDarkSecondaryContainer,
    onSecondaryContainer = mealDarkOnSecondaryContainer,
    tertiary = mealDarkTertiary,
    onTertiary = mealDarkOnTertiary,
    tertiaryContainer = mealDarkTertiaryContainer,
    onTertiaryContainer = mealDarkOnTertiaryContainer,
    error = mealDarkError,
    onError = mealDarkOnError,
    errorContainer = mealDarkErrorContainer,
    onErrorContainer = mealDarkOnErrorContainer,
    background = mealDarkBackground,
    onBackground = mealDarkOnBackground,
    surface = mealDarkSurface,
    onSurface = mealDarkOnSurface,
    inverseSurface = mealDarkInverseSurface,
    inverseOnSurface = mealDarkInverseOnSurface,
    surfaceVariant = mealDarkSurfaceVariant,
    onSurfaceVariant = mealDarkOnSurfaceVariant,
    outline = mealDarkOutline
)

private val mealLightColorScheme = lightColorScheme(
    primary = mealLightPrimary,
    onPrimary = mealLightOnPrimary,
    primaryContainer = mealLightPrimaryContainer,
    onPrimaryContainer = mealLightOnPrimaryContainer,
    inversePrimary = mealLightPrimaryInverse,
    secondary = mealLightSecondary,
    onSecondary = mealLightOnSecondary,
    secondaryContainer = mealLightSecondaryContainer,
    onSecondaryContainer = mealLightOnSecondaryContainer,
    tertiary = mealLightTertiary,
    onTertiary = mealLightOnTertiary,
    tertiaryContainer = mealLightTertiaryContainer,
    onTertiaryContainer = mealLightOnTertiaryContainer,
    error = mealLightError,
    onError = mealLightOnError,
    errorContainer = mealLightErrorContainer,
    onErrorContainer = mealLightOnErrorContainer,
    background = mealLightBackground,
    onBackground = mealLightOnBackground,
    surface = mealLightSurface,
    onSurface = mealLightOnSurface,
    inverseSurface = mealLightInverseSurface,
    inverseOnSurface = mealLightInverseOnSurface,
    surfaceVariant = mealLightSurfaceVariant,
    onSurfaceVariant = mealLightOnSurfaceVariant,
    outline = mealLightOutline
)

@Composable
fun MealAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val mealColorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> mealDarkColorScheme
        else -> mealLightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = mealColorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = mealColorScheme,
        typography = mealTypography,
        shapes = shapes,
        content = content
    )
}
