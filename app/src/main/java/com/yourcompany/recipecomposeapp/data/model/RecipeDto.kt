package com.yourcompany.recipecomposeapp.data.model

data class RecipeDto(
    val id: Int,
    val title: String,
    val ingredients: List<IngredientDto>,
    val method: String,
    val imageUrl: String,
    val isFavorite: Boolean,
    val servings: Int,
)