package com.tecsup.store.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Vino = Color(0xFF8E1B2C)
private val VinoOscuro = Color(0xFF5C101C)
private val Crema = Color(0xFFFFF8F6)
private val Tinta = Color(0xFF1C1214)

private val Esquema = lightColorScheme(
    primary = Vino,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFD9DE),
    onPrimaryContainer = VinoOscuro,
    secondary = Color(0xFF3E4A3A),
    secondaryContainer = Color(0xFFDCE6D4),
    background = Crema,
    surface = Color.White,
    onSurface = Tinta,
    surfaceVariant = Color(0xFFF3E6E8)
)

@Composable
fun TecsupStoreTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = Esquema,
        content = content
    )
}
