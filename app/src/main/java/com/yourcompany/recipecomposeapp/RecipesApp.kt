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
                                onClickCategory = { categoryId, categoryTitle, categoryImageUrl ->
                                    navController.navigate(
                                        Destination.Recipes.createRoute(
                                            categoryId, categoryTitle, categoryImageUrl
                                        )
                                    )
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
                                }
                            )
                        }
                    }

                    composable(
                        route = Destination.Recipes.route,
                        arguments = listOf(
                            navArgument("categoryId") { type = NavType.IntType },
                            navArgument("categoryTitle") { type = NavType.StringType },
                            navArgument("categoryImageUrl") { type = NavType.StringType })
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(paddingValues)
                                .fillMaxSize(),
                            contentAlignment = Alignment.TopStart
                        ) {
                            RecipesScreen(
                                onRecipeClick = { recipeId ->
                                    navController.navigate(
                                        Destination.Ingredients.createRoute(
                                            recipeId
                                        )
                                    )
                                },
                            )
                        }
                    }

                    composable(
                        route = Destination.Ingredients.route,
                        arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
                    ) {
                        RecipeDetailsScreen()
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
