package com.yourcompany.recipecomposeapp

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.yourcompany.recipecomposeapp.ui.categories.CategoriesScreen
import com.yourcompany.recipecomposeapp.ui.details.RecipeDetailsScreen
import com.yourcompany.recipecomposeapp.ui.favorites.FavoriteScreen
import com.yourcompany.recipecomposeapp.ui.navigation.BottomNavigation
import com.yourcompany.recipecomposeapp.ui.navigation.Destination
import com.yourcompany.recipecomposeapp.ui.recipes.RecipesScreen
import com.yourcompany.recipecomposeapp.ui.recipes.model.RecipeUiModel
import com.yourcompany.recipecomposeapp.ui.theme.RecipeComposeAppTheme
import kotlinx.coroutines.delay

@Preview
@Composable
fun RecipesApp(
    intent: Intent? = null
) {
    RecipeComposeAppTheme() {
        var selectedCategoryTitle by remember { mutableStateOf<String?>(null) }
        val navController = rememberNavController()

        AppNavHost(navController = navController, deepLinkIntent = intent)

        Scaffold(
            content = { paddingValues ->

                NavHost(
                    navController = navController,
                    startDestination = Destination.Categories.route
                ) {

                    composable(route = Destination.Categories.route) {
                        Box(
                            modifier = Modifier
                                .padding(paddingValues)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CategoriesScreen(
                                onClickCategory = { categoryId ->
                                    navController.navigate(
                                        Destination.Recipes.createRoute(
                                            categoryId
                                        )
                                    )
                                    selectedCategoryTitle =
                                        RecipesRepositoryStub.getCategoryByCategoryId(categoryId)?.title
                                }
                            )
                        }
                    }

                    composable(route = Destination.Favorites.route) {
                        Box(
                            modifier = Modifier
                                .padding(paddingValues)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) { FavoriteScreen() }
                    }

                    composable(
                        route = Destination.Recipes.route,
                        arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0
                        Box(
                            modifier = Modifier
                                .padding(paddingValues)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            RecipesScreen(
                                categoryId = categoryId,
                                onRecipeClick = { recipeId, recipeUiModel ->
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "recipe",
                                        recipeUiModel
                                    )
                                    navController.navigate(
                                        Destination.Ingredients.createRoute(
                                            recipeId
                                        )
                                    )
                                },
                                titleRecipeScreen = selectedCategoryTitle
                                    ?: error("Category title is required")
                            )
                        }
                    }

                    composable(
                        route = Destination.Ingredients.route,
                        arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
                    ) {
                        val recipe =
                            navController.previousBackStackEntry?.savedStateHandle?.get<RecipeUiModel>(
                                Constants.KEY_RECIPE_OBJECT
                            )
                        RecipeDetailsScreen(
                            recipe = recipe,
                            onFavoriteToggle = { isFavorite -> !isFavorite }
                        )
                    }
                }
            },
            bottomBar = {
                BottomNavigation(
                    onCategoriesClick = {
                        navController.navigate(Destination.Categories.route)
                    },
                    onFavoriteClick = {
                        navController.navigate(Destination.Favorites.route)
                    },
                )
            }
        )
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    deepLinkIntent: Intent? = null
) {
    LaunchedEffect(deepLinkIntent) {
        deepLinkIntent?.data?.let { uri ->
            val recipeId: Int? = when (uri.scheme) {
                "recipeapp" ->
                    if (uri.host == "recipe") uri.pathSegments[0].toIntOrNull() else null

                "https", "http" ->
                    if (uri.pathSegments[0] == "recipe") uri.pathSegments[1].toIntOrNull() else null

                else -> null
            }

            if (recipeId != null) {
                delay(100)
                navController.navigate(Destination.Ingredients.createRoute(recipeId))
            }
        }
    }
}
