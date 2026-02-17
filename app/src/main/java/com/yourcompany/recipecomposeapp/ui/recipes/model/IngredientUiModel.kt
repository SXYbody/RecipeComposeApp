package com.yourcompany.recipecomposeapp.ui.recipes.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.yourcompany.recipecomposeapp.data.model.IngredientDto
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class IngredientUiModel(
    val quantity: String,
    val amount: String,
    val description: String,
): Parcelable

fun IngredientDto.toUiModel() = IngredientUiModel(
    quantity = quantity.toString(),
    amount = unitOfMeasure,
    description = description,
)