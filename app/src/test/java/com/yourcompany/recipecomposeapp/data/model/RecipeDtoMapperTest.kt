package com.yourcompany.recipecomposeapp.data.model

import com.yourcompany.recipecomposeapp.Constants
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.toUiModel
import fixtures.RecipeTestFixtures
import junit.framework.TestCase.assertEquals
import org.junit.Test

class RecipeDtoMapperTest {
    @Test
    fun `maps DTO to UI model correctly`() {
        val dto = RecipeTestFixtures.createRecipeDto()

        val result = dto.toUiModel()

        assertEquals(1, result.id)
        assertEquals("Pasta Carbonara", result.title)
    }

    @Test
    fun `prepends base url to relative imageUrl`() {
        val dto = RecipeTestFixtures.createRecipeDto(imageUrl = "pasta.jpg")

        val result = dto.toUiModel()

        assertEquals(Constants.IMAGES_BASE_URL + "pasta.jpg", result.imageUrl)
    }

    @Test
    fun `preserves full imageUrl starting with http`() {
        val fullUrl = "https://example.com/image.jpg"
        val dto = RecipeTestFixtures.createRecipeDto(imageUrl = fullUrl)

        val result = dto.toUiModel()

        assertEquals(fullUrl, result.imageUrl)
    }
}