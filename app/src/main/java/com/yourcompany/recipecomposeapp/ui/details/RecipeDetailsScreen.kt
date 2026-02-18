package com.yourcompany.recipecomposeapp.ui.details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.yourcompany.recipecomposeapp.ui.recipes.model.RecipeUiModel

@Composable
fun RecipeDetailsScreen(
    recipe: RecipeUiModel? = null
) {
    if (recipe != null) {
        Text(recipe.title)
        Text(recipe.method)
    } else { Text("рецепт не найден!") }
}