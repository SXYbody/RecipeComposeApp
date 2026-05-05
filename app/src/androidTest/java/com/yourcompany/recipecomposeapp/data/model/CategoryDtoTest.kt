package com.yourcompany.recipecomposeapp.data.model

import com.yourcompany.recipecomposeapp.features.categories.presentation.model.toUiModel
import junit.framework.TestCase.assertEquals
import org.junit.Test

class CategoryDtoTest {
    @Test
    fun `converts_DTO_to_UI_model`() {
        val dto = CategoryDto(
            id = 1,
            title = "Завтраки",
            description = "Утренние блюда",
            imageUrl = "breakfast.jpg"
        )

        val result = dto.toUiModel()

        assertEquals("Завтраки", result.title)
    }
}