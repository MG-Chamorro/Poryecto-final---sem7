package ni.edu.uam.proyectofinal_sem7.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val ColorScheme = lightColorScheme(
    primary = EscolarRojo,
    secondary = EscolarGrisOscuro,
    background = EscolarCrema,
    surface = EscolarCrema,
    onPrimary = EscolarCrema,
    onBackground = EscolarGrisOscuro
)

@Composable
fun FlowItTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        content = content
    )
}