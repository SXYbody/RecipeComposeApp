package com.yourcompany.recipecomposeapp.ui.recipes.model

import androidx.collection.arrayMapOf
import androidx.compose.runtime.Immutable
import com.yourcompany.recipecomposeapp.Constants
import com.yourcompany.recipecomposeapp.data.model.IngredientDto
import com.yourcompany.recipecomposeapp.data.model.RecipeDto

@Immutable
class IngredientUiModel(
    val name: String,
    val amount: String,
    val description: String,
)

fun IngredientDto.toUiModel() = IngredientUiModel(
    name = quantity.toString(),
    amount = unitOfMeasure,
    description = description,
)