package com.yourcompany.recipecomposeapp.data.repository

import android.util.Log
import com.yourcompany.recipecomposeapp.data.model.CategoryDto
import com.yourcompany.recipecomposeapp.data.model.RecipeDto
import com.yourcompany.recipecomposeapp.features.core.network.api.RecipesApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class RecipesRepositoryImpl(
    private val recipesApiService: RecipesApiService,
) : RecipesRepository {
    override suspend fun getCategories(): List<CategoryDto> {
        return withContext(Dispatchers.IO) {
            try {
                recipesApiService.getCategories()
            } catch (e: Exception) {
                Log.e("RecipesRepository", "Ошибка загрузки категории", e)
                emptyList()
            }
        }
    }

    override suspend fun getRecipesByCategory(categoryId: Int): List<RecipeDto> {
        return withContext(Dispatchers.IO) {
            try {
                recipesApiService.getRecipesByCategory(categoryId)
            } catch (e: Exception) {
                Log.e("RecipesRepository", "Ошибка загрузки рецептов", e)
                emptyList()
            }
        }
    }

    override suspend fun getRecipe(recipeId: Int): RecipeDto {
        return withContext(Dispatchers.IO) {
            try {
                recipesApiService.getRecipeById(recipeId)
            } catch (e: Exception) {
                Log.e("RecipesRepository", "Ошибка загрузки рецепта", e)
                throw e
            }
        }
    }
}