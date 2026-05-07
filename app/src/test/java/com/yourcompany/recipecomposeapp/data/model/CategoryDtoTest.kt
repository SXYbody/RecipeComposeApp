package com.yourcompany.recipecomposeapp.data.model

import com.yourcompany.recipecomposeapp.features.categories.presentation.model.toUiModel
import fixtures.CategoryTestFixtures
import junit.framework.TestCase.assertEquals
import org.junit.Test

class CategoryDtoTest {
    @Test
    fun `mapper maps empty title correctly`() {
        val dto = CategoryTestFixtures.createCategoryDto(title = "")

        val result = dto.toUiModel()

        assertEquals("", result.title)
    }

    @Test
    fun `mapper preserves very long description`() {
        val longDescription = "А".repeat(500)
        val dto = CategoryTestFixtures.createCategoryDto(description = longDescription)

        val result = dto.toUiModel()

        assertEquals(longDescription, result.description)
    }
}