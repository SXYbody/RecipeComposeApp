package com.yourcompany.recipecomposeapp.features.recipes.presentation.model

data class RecipesUiState(
    val categoryTitle: String = "",
    val categoryImageUrl: String = "",
    val recipesList: List<RecipeUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
) {
    val isEmpty: Boolean get() = recipesList.isEmpty()
}