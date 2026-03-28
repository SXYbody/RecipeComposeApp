package com.yourcompany.recipecomposeapp.features.details.presentation.model

import androidx.compose.runtime.remember
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.IngredientUiModel
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.toUiModel

data class RecipeDetailsUiState(
    val recipe: RecipeUiModel,
    val isFavoriteSave: Boolean = false,
    val currentPortions: Int = 1,
    val isLoading: Boolean = false,
    val error: String? = null,
) {
    private val multiplier get() = currentPortions.toDouble() / recipe.servings
    val scaledIngredients: List<IngredientUiModel>
        get() = recipe.ingredients.map { ingredient ->
            ingredient.copy(
                quantity = ingredient.quantity * multiplier
            ).toUiModel()
        }
}