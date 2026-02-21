package com.yourcompany.recipecomposeapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IngredientDto(
    val quantity: Double,
    val unitOfMeasure: String,
    val description: String,
): Parcelable