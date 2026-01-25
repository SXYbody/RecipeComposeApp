package com.yourcompany.recipecomposeapp.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourcompany.recipecomposeapp.montserratAlternatesFontFamily
import com.yourcompany.recipecomposeapp.montserratFontFamily

val recipesAppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = montserratAlternatesFontFamily,
        fontStyle = FontStyle.Normal,
        fontSize = 20.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = montserratAlternatesFontFamily,
        fontStyle = FontStyle.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = montserratAlternatesFontFamily,
        fontStyle = FontStyle.Normal,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = montserratAlternatesFontFamily,
        fontStyle = FontStyle.Normal,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = montserratFontFamily,
        fontStyle = FontStyle.Normal,
        fontSize = 16.sp
    )
)

@Preview(showBackground = true)
@Composable
fun TypographyPreview() {
    RecipeComposeAppTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("displayLarge - Заголовки экранов", style = MaterialTheme.typography.displayLarge)
            Text("titleMedium - Карточки", style = MaterialTheme.typography.titleMedium)
            Text("bodyMedium - Основной текст", style = MaterialTheme.typography.bodyMedium)
            Text("bodySmall - Мелкий текст", style = MaterialTheme.typography.bodySmall)
            Text("labelLarge - Кнопки", style = MaterialTheme.typography.labelLarge)
        }
    }
}
