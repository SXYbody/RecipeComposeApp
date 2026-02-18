package com.yourcompany.recipecomposeapp.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.ui.components.ScreenHeader
import com.yourcompany.recipecomposeapp.ui.recipes.model.RecipeUiModel

@Composable
fun RecipeDetailsScreen(
    recipe: RecipeUiModel? = null
) {
    if (recipe == null) {
        Text("рецепт не найден!")
        return
    }

    var currentPortions by remember { mutableStateOf(recipe.servings) }
    val scaledIngredients = remember(currentPortions) {
        val multiplier = currentPortions.toDouble() / recipe.servings
        recipe.ingredients.map { ingredient ->
            ingredient.copy(
                quantity = ingredient.quantity * multiplier
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ScreenHeader(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            painterContent = "Заголовок",
            text = recipe.title
        )

        PortionsSlider(
            currentPortions = currentPortions,
            onPortionsChange = { newValue ->
                currentPortions = newValue
            }
        )
        IngredientsList(recipe.ingredients)

        Text(text = recipe.method)
    }
}

@Preview
@Composable
fun RecipeDetailsScreenPreview() {
    RecipeDetailsScreen()
}