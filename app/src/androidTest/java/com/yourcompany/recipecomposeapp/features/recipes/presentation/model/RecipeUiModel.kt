package com.yourcompany.recipecomposeapp.features.recipes.presentation.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.yourcompany.recipecomposeapp.Constants
import com.yourcompany.recipecomposeapp.data.model.IngredientDto
import com.yourcompany.recipecomposeapp.data.model.RecipeDto
import kotlinx.parcelize.Parcelize


@Immutable
@Parcelize
data class RecipeUiModel(
    val id: Int,
    val title: String,
    val ingredients: List<IngredientDto>,
    val method: List<String>,
    val imageUrl: String,
    val isFavorite: Boolean,
    val servings: Int,
): Parcelable
fun RecipeDto.toUiModel() = RecipeUiModel(
    id = id,
    title = title,
    ingredients = ingredients,
    method = method,
    imageUrl = if (imageUrl.startsWith("http")) imageUrl else Constants.IMAGES_BASE_URL + imageUrl,
    isFavorite = isFavorite,
    servings = servings
)