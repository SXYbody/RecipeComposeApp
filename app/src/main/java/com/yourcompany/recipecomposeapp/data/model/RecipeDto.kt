package com.yourcompany.recipecomposeapp.data.model

import com.yourcompany.recipecomposeapp.data.database.convert.Converters
import com.yourcompany.recipecomposeapp.data.database.entity.RecipeEntity
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDto(
    val id: Int,
    val title: String,
    val ingredients: List<IngredientDto>,
    val method: List<String>,
    val imageUrl: String,
    val isFavorite: Boolean = false,
    val servings: Int = 1,
)

fun RecipeDto.toEntity(categoryId: Int) = RecipeEntity(
    id = id,
    title = title,
    ingredients = Converters().fromList(ingredients.map { "${it.quantity}|${it.unitOfMeasure}|${it.description}" }),
    method = Converters().fromList(method),
    imageUrl = imageUrl,
    categoryId = categoryId,
)

fun RecipeEntity.toDto() = RecipeDto(
    id = id,
    title = title,
    ingredients = Converters().fromString(ingredients).map {
        val parts = it.split("|")
        IngredientDto(
            quantity = parts[0],
            unitOfMeasure = parts[1],
            description = parts[2]
        )
    },
    method = Converters().fromString(method),
    imageUrl = imageUrl,
)