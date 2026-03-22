package com.yourcompany.recipecomposeapp.features.core.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.toUiModel

class FavoritePrefsManager (
    context: Context
) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("recipe_app_prefs", Context.MODE_PRIVATE)!!

    fun isFavorite(recipeId: Int): Boolean {
        val favoriteRecipeIds = sharedPreferences.getStringSet("favorite_recipe_ids", emptySet())
        val filterRecipeId: String? = favoriteRecipeIds?.firstOrNull() { it.toInt() == recipeId }

        if (filterRecipeId == null) return false
        return true
    }

    fun addToFavorite(recipeId: Int) {
        val currentFavorites = sharedPreferences.getStringSet("favorite_recipe_ids", emptySet())

        val updatedFavorites = currentFavorites?.toMutableSet() ?: mutableSetOf()
        updatedFavorites.add(recipeId.toString())

        sharedPreferences.edit { putStringSet("favorite_recipe_ids", updatedFavorites) }

    }

    fun removeToFavorite(recipeId: Int) {
        val currentFavorites = sharedPreferences.getStringSet("favorite_recipe_ids", emptySet())

        val updatedFavorites = currentFavorites?.toMutableSet() ?: mutableSetOf()
        updatedFavorites.remove(recipeId.toString())

        sharedPreferences.edit { putStringSet("favorite_recipe_ids", updatedFavorites) }
    }

    fun getAllFavorites(): Set<String>? =
        sharedPreferences.getStringSet("favorite_recipe_ids", emptySet())

    fun getAllFavoritesToModels(): List<RecipeUiModel> {
        val allFavoritesSet = getAllFavorites() ?: return emptyList()

        val allFavoritesUiModel = allFavoritesSet.map { RecipesRepositoryStub.getRecipeById(it.toInt())!!.toUiModel() }
        return allFavoritesUiModel
    }
}
