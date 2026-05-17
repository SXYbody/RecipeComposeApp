package com.yourcompany.recipecomposeapp.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yourcompany.recipecomposeapp.data.database.dao.CategoryDao
import com.yourcompany.recipecomposeapp.data.database.dao.RecipeDao
import com.yourcompany.recipecomposeapp.data.database.entity.CategoryEntity
import com.yourcompany.recipecomposeapp.data.database.entity.RecipeEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecipesDaoTest {

    private lateinit var database: RecipesDatabase
    private lateinit var categoryDao: CategoryDao
    private lateinit var recipeDao: RecipeDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, RecipesDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        categoryDao = database.categoryDao()
        recipeDao = database.recipeDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertsAndRetrievesCategories() = runTest {
        val categories = listOf(
            CategoryEntity(id = 1, name = "Завтраки", description = "Лёгкие", imageUrl = ""),
            CategoryEntity(id = 2, name = "Обеды", description = "Основные", imageUrl = "")
        )

        categoryDao.insertOrUpdateCategory(categories)
        val retrieved = categoryDao.getAllCategories().first()

        assertEquals(2, retrieved.size)
    }

    @Test
    fun insertReplacesDuplicateCategory() = runTest {
        val categories = listOf(
            CategoryEntity(id = 1, name = "Завтраки", description = "Лёгкие", imageUrl = ""),
            CategoryEntity(id = 1, name = "Обеды", description = "Основные", imageUrl = "")
        )
        categoryDao.insertOrUpdateCategory(categories)
        val retrieved = categoryDao.getAllCategories().first()
        assertEquals(1, retrieved.size)

    }

    @Test
    fun getRecipesByCategoryReturnsCorrectItems() = runTest {
        val categories = listOf(
            CategoryEntity(id = 1, name = "Завтраки", description = "Лёгкие", imageUrl = ""),
            CategoryEntity(id = 2, name = "Обеды", description = "Основные", imageUrl = "")
        )

        val recipes = listOf(
            RecipeEntity(
                id = 1,
                title = "Каша",
                categoryId = 1,
                imageUrl = "",
                ingredients = "",
                method = ""
            ),
            RecipeEntity(
                id = 2,
                title = "Каша",
                categoryId = 2,
                imageUrl = "",
                ingredients = "",
                method = ""
            ),
            RecipeEntity(
                id = 3,
                title = "Каша",
                categoryId = 2,
                imageUrl = "",
                ingredients = "",
                method = ""
            ),
        )

        categoryDao.insertOrUpdateCategory(categories)
        recipeDao.insertOrUpdateRecipe(recipes)
        assertEquals(1, recipeDao.getRecipesByCategoryId(1).first().size)
    }

    @Test
    fun emptyDatabaseReturnsEmptyList() = runTest {
        assertEquals(0, categoryDao.getAllCategories().first().size)
    }
}