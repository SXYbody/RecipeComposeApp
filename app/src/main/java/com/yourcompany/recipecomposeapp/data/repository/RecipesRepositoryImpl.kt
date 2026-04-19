package com.yourcompany.recipecomposeapp.data.repository

import android.util.Log
import com.yourcompany.recipecomposeapp.data.database.RecipesDatabase
import com.yourcompany.recipecomposeapp.data.model.CategoryDto
import com.yourcompany.recipecomposeapp.data.model.RecipeDto
import com.yourcompany.recipecomposeapp.data.model.toDto
import com.yourcompany.recipecomposeapp.data.model.toEntity
import com.yourcompany.recipecomposeapp.features.core.network.api.RecipesApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext


class RecipesRepositoryImpl(
    private val recipesApiService: RecipesApiService,
    private val database: RecipesDatabase,
) : RecipesRepository {
    private val categoryDao = database.categoryDao()
    private val recipeDao = database.recipeDao()

    override fun getCategories(): Flow<List<CategoryDto>> {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val fresh = recipesApiService.getCategories()
                categoryDao.insertOrUpdateCategory(fresh.map { it.toEntity() })
            } catch (e: Exception) {
                Log.e("RecipesRepository", "Ошибка загрузки категории", e)
            }
        }
        return categoryDao.getAllCategories().map { entities -> entities.map { it.toDto() } }
    }

    override fun getRecipesByCategory(categoryId: Int): Flow<List<RecipeDto>> {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val fresh = recipesApiService.getRecipesByCategory(categoryId)
                recipeDao.insertOrUpdateRecipe(fresh.map { it.toEntity(categoryId) })
            } catch (e: Exception) {
                Log.e("RecipesRepository", "Ошибка загрузки рецептов", e)
            }
        }
        return recipeDao.getRecipesByCategoryId(categoryId).map { entities -> entities.map { it.toDto() } }
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