package com.yourcompany.recipecomposeapp.ui.recipes.model

import androidx.compose.runtime.Immutable
import com.yourcompany.recipecomposeapp.Constants
import com.yourcompany.recipecomposeapp.data.model.IngredientDto
import com.yourcompany.recipecomposeapp.data.model.RecipeDto
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Immutable
@Parcelize
data class RecipeUiModel(
    val id: Int,
    val title: String,
    val ingredients: List<IngredientDto>,
    val method: String,
    val imageUrl: String,
    val isFavorite: Boolean,
    val servings: Int,
): Parcelable
fun RecipeDto.toUiModel() = RecipeUiModel(
    id = id,
    title = title,
    ingredients = ingredients,
    method = method,
    imageUrl = if (imageUrl.startsWith("http")) imageUrl else Constants.ASSETS_URI_PREFIX + imageUrl,
    isFavorite = isFavorite,
    servings = servings
)