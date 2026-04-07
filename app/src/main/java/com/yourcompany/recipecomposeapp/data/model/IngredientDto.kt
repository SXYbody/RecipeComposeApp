package com.yourcompany.recipecomposeapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class IngredientDto(
    val quantity: String,
    val unitOfMeasure: String,
    val description: String,
): Parcelable