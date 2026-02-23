package com.yourcompany.recipecomposeapp.data.repository

import com.yourcompany.recipecomposeapp.data.model.CategoryDto
import com.yourcompany.recipecomposeapp.data.model.IngredientDto
import com.yourcompany.recipecomposeapp.data.model.RecipeDto
import com.yourcompany.recipecomposeapp.ui.categories.model.CategoryUiModel
import com.yourcompany.recipecomposeapp.ui.categories.model.toUiModel

object RecipesRepositoryStub {
    private val categories: List<CategoryDto> = listOf(
        CategoryDto(
            0,
            "Бургеры",
            "Рецепты всех популярных видов бургеров",
            "burger.png"
        ),
        CategoryDto(
            1,
            "Десерты",
            "Самые вкусные рецепты десертов специально для вас",
            "dessert.png"
        ),
    )

    private val burgerRecipes: List<RecipeDto> = listOf(
        RecipeDto(
            0,
            "Классический бургер с говядиной",
            listOf(
                IngredientDto(
                    0.5,
                    "кг",
                    "говяжий фарш"
                ),
                IngredientDto(
                    1.5,
                    "шт",
                    "луковица, мелко нарезанная"
                ),
                IngredientDto(
                    2.0,
                    "зубч",
                    "чеснок, измельченный",
                ),
            ),
            "\"1. В глубокой миске смешайте говяжий фарш, лук, чеснок, соль и перец. Разделите фарш на 4 равные части и сформируйте котлеты.\",\n" +
                    "            \"2. Разогрейте сковороду на среднем огне. Обжаривайте котлеты с каждой стороны в течение 4-5 минут или до желаемой степени прожарки.\",\n" +
                    "            \"3. В то время как котлеты готовятся, подготовьте булочки. Разрежьте их пополам и обжарьте на сковороде до золотистой корочки.\",\n" +
                    "            \"4. Смазать нижние половинки булочек горчицей и кетчупом, затем положите лист салата, котлету, кольца помидора и закройте верхней половинкой булочки.\",\n" +
                    "            \"5. Подавайте бургеры горячими с картофельными чипсами или картофельным пюре.\"",
            "https://images.google.com",
            false,
            1
        )
    )

    private val desertRecipes: List<RecipeDto> = listOf()

    fun getCategories(): List<CategoryDto> {
        return categories
    }

    fun getCategoryByCategoryId(categoryId: Int): CategoryUiModel? {
        categories.forEach { if (it.id == categoryId) return it.toUiModel() }
        return null
    }

    fun getRecipesByCategoryId(categoryId: Int): List<RecipeDto> {
        return when (categoryId) {
            0 -> burgerRecipes
            1 -> desertRecipes
            else -> emptyList()
        }
    }

    fun getRecipeById(recipeId: Int): RecipeDto? {
        return burgerRecipes.find { it.id == recipeId }
    }
}