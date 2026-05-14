package com.yourcompany.recipecomposeapp.features.recipes.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.RecipesUiState
import org.junit.Rule
import org.junit.Test

class RecipesScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun showsLoadingState() {
        composeTestRule.setContent {
            RecipeContent(
                uiState = RecipesUiState(
                    isLoading = true
                ),
                onRecipeClick = { _ -> }
            )
        }

        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }

    @Test
    fun showsErrorState() {
        composeTestRule.setContent {
            RecipeContent(
                uiState = RecipesUiState(
                    error = "Network error"
                ),
                onRecipeClick = { _ -> }
            )
        }

        composeTestRule.onNodeWithTag("error_message").assertIsDisplayed()
    }

    @Test
    fun showsEmptyState() {
        composeTestRule.setContent {
            RecipeContent(
                uiState = RecipesUiState(),
                onRecipeClick = { _ -> }
            )
        }

        composeTestRule.onNodeWithTag("empty_state").assertIsDisplayed()
    }

    @Test
    fun displaysRecipeList() {
        composeTestRule.setContent {
            RecipeContent(
                uiState = RecipesUiState(
                    recipesList = listOf(
                        RecipeUiModel(
                            id = 1,
                            title = "Пирожки",
                            ingredients = listOf(),
                            method = listOf(),
                            imageUrl = "",
                            isFavorite = false,
                            servings = 1
                        )
                    )
                ),
                onRecipeClick = { _ -> }
            )
        }

        composeTestRule.onNodeWithText("ПИРОЖКИ").assertIsDisplayed()
    }
}