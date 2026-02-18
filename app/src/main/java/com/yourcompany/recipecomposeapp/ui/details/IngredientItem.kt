package com.yourcompany.recipecomposeapp.ui.details

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Card
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.yourcompany.recipecomposeapp.data.model.IngredientDto
import kotlin.math.roundToInt

@Composable
fun IngredientItem(
    title: String,
    quantity: Double,
    unitOfMeasure: String,
) {
    val measureText = when {
        quantity >= 0.75 -> "3/4"
        quantity >= 0.5 -> "1/2"
        quantity >= 0.25 -> "1/4"
        else -> "щепотка"
    }
    Card {
        Row {
            Text(title)
            Text("$measureText")
        }
    }

}

@Composable
fun PortionsSlider(
    currentPortions: Int,
    onPortionsChange: (Int) -> Unit
) {
    Slider(
        value = currentPortions.toFloat(),
        onValueChange = { onPortionsChange(it.roundToInt()) },
        valueRange = 1f..12f,
        steps = 10
    )
}

@Composable
fun IngredientsList(
    list: List<IngredientDto>
) {
    list.forEach { ingredient ->
        IngredientItem(
            title = ingredient.description,
            quantity = ingredient.quantity,
            unitOfMeasure = ingredient.unitOfMeasure
        )
    }
}

