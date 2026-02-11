package com.yourcompany.recipecomposeapp.ui.recipes.model

import androidx.compose.runtime.Immutable
import com.yourcompany.recipecomposeapp.data.model.IngredientDto

@Immutable
data class IngredientUiModel(
    val quantity: String,
    val amount: String,
    val description: String,
)

fun IngredientDto.toUiModel() = IngredientUiModel(
    quantity = quantity.toString(),
    amount = unitOfMeasure,
    description = description,
)