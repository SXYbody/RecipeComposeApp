package com.yourcompany.recipecomposeapp.features.favorites.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.yourcompany.recipecomposeapp.features.core.ui.components.ErrorScreen
import com.yourcompany.recipecomposeapp.features.core.ui.components.LoadingScreen
import com.yourcompany.recipecomposeapp.features.core.ui.components.ScreenHeader
import com.yourcompany.recipecomposeapp.features.favorites.presentation.FavoritesViewModel
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.yourcompany.recipecomposeapp.features.recipes.ui.RecipeItem

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    onClickRecipe: (Int) -> Unit,
) {
    val viewModel: FavoritesViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY])
                FavoritesViewModel(
                    application = application,
                )
            }
        }
    )
    val uiState by viewModel.uiState.collectAsState()

    val recipes: List<RecipeUiModel> = uiState.recipes

    Column {
        ScreenHeader(
            image = "file:///android_asset/favorites.png",
            imageContentDescription = "Заголовок категорий",
            text = "ИЗБРАННОЕ",
        )

        when {
            uiState.isLoading -> LoadingScreen()

            uiState.error != null -> ErrorScreen("Не удалось загрузить избранное")

            recipes.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Вы ещё не добавили ни одного рецепта в избранное",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(
                        items = recipes,
                        key = { it.id }
                    ) { recipe ->
                        RecipeItem(
                            recipe = recipe,
                            onRecipeClick = {
                                onClickRecipe(recipe.id)
                            },
                        )
                    }
                }

            }
        }
    }
}