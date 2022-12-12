package cat.martori.pickleapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = LightGreenDarker,
    primaryVariant = LightGreen800,
    secondary = Amber800,
    secondaryVariant = AmberDarker,
    onPrimary = Color.White,
    onSecondary = Color.Black,
)

private val LightColorPalette = lightColors(
    primary = LightGreen800,
    primaryVariant = LightGreenLighter,
    secondary = Amber800,
    secondaryVariant = AmberLighter,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
)

@Composable
fun PickleAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}