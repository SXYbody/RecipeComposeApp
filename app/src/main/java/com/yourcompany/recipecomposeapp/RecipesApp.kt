package com.yourcompany.recipecomposeapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.yourcompany.recipecomposeapp.ui.categories.CategoriesScreen
import com.yourcompany.recipecomposeapp.ui.favorites.FavoriteScreen
import com.yourcompany.recipecomposeapp.ui.navigation.BottomNavigation
import com.yourcompany.recipecomposeapp.ui.recipes.RecipesScreen
import com.yourcompany.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Preview
@Composable
fun RecipesApp() {
    RecipeComposeAppTheme() {
        var screen by remember { mutableStateOf(ScreenId.CATEGORIES) }
        var selectedCategoryId by remember { mutableStateOf<Int?>(null) }
        var selectedCategoryTitle by remember { mutableStateOf<String?>(null) }

        Scaffold(
            content = { paddingValues ->
                when (screen) {
                    ScreenId.CATEGORIES -> {
                        Box(
                            modifier = Modifier
                                .padding(paddingValues)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CategoriesScreen(
                                onClickCategory = { categoryId ->
                                    selectedCategoryId = categoryId
                                    selectedCategoryTitle =
                                        RecipesRepositoryStub.getCategoryByCategoryId(categoryId)?.title
                                    screen = ScreenId.RECIPES
                                }
                            )
                        }
                    }

                    ScreenId.FAVORITES -> {
                        Box(
                            modifier = Modifier
                                .padding(paddingValues)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) { FavoriteScreen() }
                    }

                    ScreenId.RECIPES -> {
                        Box(
                            modifier = Modifier
                                .padding(paddingValues)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            RecipesScreen(
                                categoryId = selectedCategoryId ?: error("Category ID is required"),
                                onRecipeClick = { id ->

                                },
                                titleRecipeScreen = selectedCategoryTitle
                                    ?: error("Category title is required")
                            )
                        }
                    }
                }
            },
            bottomBar = {
                BottomNavigation(
                    onCategoriesClick = {
                        screen = ScreenId.CATEGORIES
                    },
                    onFavoriteClick = {
                        screen = ScreenId.FAVORITES
                    },
                )
            }
        )
    }
}

