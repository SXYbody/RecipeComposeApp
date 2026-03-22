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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.yourcompany.recipecomposeapp.features.categories.ui.CategoriesScreen
import com.yourcompany.recipecomposeapp.features.details.ui.RecipeDetailsScreen
import com.yourcompany.recipecomposeapp.features.favorites.ui.FavoriteScreen
import com.yourcompany.recipecomposeapp.features.core.ui.navigation.BottomNavigation
import com.yourcompany.recipecomposeapp.features.core.ui.navigation.Destination
import com.yourcompany.recipecomposeapp.features.recipes.ui.RecipesScreen
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.toUiModel
import com.yourcompany.recipecomposeapp.theme.RecipeComposeAppTheme
import com.yourcompany.recipecomposeapp.features.core.utils.AppDataStoreManager
import kotlinx.coroutines.delay

@Preview
@Composable
fun RecipesApp(
    intent: Intent? = null
) {
    RecipeComposeAppTheme() {
        val context = LocalContext.current
        var selectedCategoryTitle by remember { mutableStateOf<String?>(null) }
        val navController = rememberNavController()
        val appData = remember { AppDataStoreManager(context) }

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
                            contentAlignment = Alignment.TopStart
                        ) {
                            FavoriteScreen(
                                onClickRecipe = { recipeId ->
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "recipe",
                                        RecipesRepositoryStub.getRecipeById(recipeId)?.toUiModel()
                                    )
                                    navController.navigate(
                                        Destination.Ingredients.createRoute(
                                            recipeId
                                        )
                                    )
                                },
                                recipesRepository = RecipesRepositoryStub,
                                appData = AppDataStoreManager(LocalContext.current)
                            )
                        }
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
                            contentAlignment = Alignment.TopStart
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
                        if (recipe == null) return@composable

                        RecipeDetailsScreen(
                            recipe = recipe,
                            appData = appData,
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
                    appData = appData
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
