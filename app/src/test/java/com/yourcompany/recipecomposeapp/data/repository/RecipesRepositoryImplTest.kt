package com.yourcompany.recipecomposeapp.data.repository

import com.yourcompany.recipecomposeapp.data.database.RecipesDatabase
import com.yourcompany.recipecomposeapp.data.database.dao.CategoryDao
import com.yourcompany.recipecomposeapp.data.database.dao.RecipeDao
import com.yourcompany.recipecomposeapp.data.model.CategoryDto
import com.yourcompany.recipecomposeapp.data.model.RecipeDto
import com.yourcompany.recipecomposeapp.data.model.toEntity
import com.yourcompany.recipecomposeapp.features.core.network.api.RecipesApiService
import fixtures.CategoryTestFixtures
import fixtures.RecipeTestFixtures
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class RecipesRepositoryImplTest {

    private val apiService = mockk<RecipesApiService>()
    private val database = mockk<RecipesDatabase>(relaxed = true)
    private val categoryDao = mockk<CategoryDao>()
    private val recipeDao = mockk<RecipeDao>()

    private lateinit var repository: RecipesRepositoryImpl

    @Before
    fun setup() {
        every { database.categoryDao() } returns categoryDao
        every { database.recipeDao() } returns recipeDao

        repository = RecipesRepositoryImpl(apiService, database)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getCategories emits categories from database`() = runTest {
        val categoryEntities = listOf(
            CategoryTestFixtures.createCategoryDto(id = 1, title = "Завтраки").toEntity(),
            CategoryTestFixtures.createCategoryDto(id = 2, title = "Обеды").toEntity(),
        )
        every { categoryDao.getAllCategories() } returns flowOf(categoryEntities)
        coEvery { apiService.getCategories() } returns emptyList()
        coEvery { categoryDao.insertOrUpdateCategory(any()) } returns listOf(1L, 2L)

        val result = mutableListOf<List<CategoryDto>>()
        repository.getCategories().collect { categories ->
            result.add(categories)
        }

        assertEquals(1, result.size)
        assertEquals(2, result[0].size)
        assertEquals("Завтраки", result[0][0].title)
    }

    @Test
    fun `getCategories still emits data when api throws exception`() = runTest {
        val cachedCategories = listOf(
            CategoryTestFixtures.createCategoryDto(id = 1, title = "Кэшированная категория")
                .toEntity()
        )
        every { categoryDao.getAllCategories() } returns flowOf(cachedCategories)
        coEvery { apiService.getCategories() } throws Exception("Network error")

        val result = mutableListOf<List<CategoryDto>>()
        repository.getCategories().collect { categories ->
            result.add(categories)
        }

        assertEquals(1, result.size)
        assertEquals(1, result[0].size)
        assertEquals("Кэшированная категория", result[0][0].title)
    }

    @Test
    fun `getRecipesByCategory returns flow filtered by categoryId`() = runTest {
        val categoryId = 1
        val recipeEntities = listOf(
            RecipeTestFixtures.createRecipeDto(id = 1, title = "Паста").toEntity(categoryId),
            RecipeTestFixtures.createRecipeDto(id = 2, title = "Пицца").toEntity(categoryId)
        )
        every { recipeDao.getRecipesByCategoryId(categoryId) } returns flowOf(recipeEntities)
        coEvery { apiService.getRecipesByCategory(categoryId) } returns emptyList()
        coEvery { recipeDao.insertOrUpdateRecipe(any()) } returns listOf(1L, 2L)

        val result = mutableListOf<List<RecipeDto>>()
        repository.getRecipesByCategory(categoryId).collect { recipes ->
            result.add(recipes)
        }
        assertEquals(1, result.size)
        assertEquals(2, result[0].size)
        assertEquals("Паста", result[0][0].title)
        assertEquals("Пицца", result[0][1].title)
    }
}