package com.yourcompany.recipecomposeapp.features.details.presentation.model

import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.IngredientUiModel
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.toUiModel

data class RecipeDetailsUiState(
    val recipe: RecipeUiModel? = null,
    val isFavoriteSave: Boolean = false,
    val currentPortions: Int = 1,
    val isLoading: Boolean = false,
    val error: String? = null,
) {
    val scaledIngredients: List<IngredientUiModel>?
        get() = recipe?.let {
            val multiplier = currentPortions.toDouble() / recipe.servings
            it.ingredients.map { ingredient ->
                ingredient.copy(
                    quantity = (ingredient.quantity.toDouble() * multiplier).toString()
                ).toUiModel()
            }
        }
}