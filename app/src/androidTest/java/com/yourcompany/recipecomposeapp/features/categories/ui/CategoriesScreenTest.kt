package com.yourcompany.recipecomposeapp.features.categories.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.runner.AndroidJUnit4
import com.yourcompany.recipecomposeapp.features.categories.presentation.model.CategoriesUiState
import com.yourcompany.recipecomposeapp.features.categories.presentation.model.CategoryUiModel
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CategoriesScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysCategories() {
        composeTestRule.setContent {
            CategoriesContent(
                uiState = CategoriesUiState(
                    categories = listOf(
                        CategoryUiModel(
                            id = 1,
                            title = "Завтраки",
                            description = "",
                            imageUrl = ""
                        )
                    )
                ),
                onClickCategory = { _, _, _ -> }
            )
        }
        composeTestRule.onNodeWithText("ЗАВТРАКИ").assertIsDisplayed()
    }

    @Test
    fun clickingCategoryNavigatesToRecipes() {
        var clickedId: Int? = null
        composeTestRule.setContent {
            CategoriesContent(
                uiState = CategoriesUiState(
                    categories = listOf(
                        CategoryUiModel(
                            id = 1,
                            title = "Завтраки",
                            description = "",
                            imageUrl = ""
                        )
                    )
                ),
                onClickCategory = { id, _, _ -> clickedId = id }
            )
        }
        composeTestRule.onNodeWithText("ЗАВТРАКИ").performClick()
        assertEquals(1, clickedId)
    }

    @Test
    fun showsLoadingState() {
        composeTestRule.setContent {
            CategoriesContent(
                uiState = CategoriesUiState(
                    isLoading = true
                ),
                onClickCategory = { _, _, _ -> }
            )
        }
        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }
}
