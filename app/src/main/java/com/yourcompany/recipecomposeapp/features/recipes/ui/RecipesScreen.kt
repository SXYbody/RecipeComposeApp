package com.yourcompany.recipecomposeapp.features.recipes.ui

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yourcompany.recipecomposeapp.features.core.ui.components.ErrorScreen
import com.yourcompany.recipecomposeapp.features.core.ui.components.LoadingScreen
import com.yourcompany.recipecomposeapp.features.core.ui.components.ScreenHeader
import com.yourcompany.recipecomposeapp.features.recipes.presentation.RecipesViewModel
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel

@Composable
fun RecipesScreen(
    viewModel: RecipesViewModel,
    modifier: Modifier = Modifier,
    onRecipeClick: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val recipes: List<RecipeUiModel> = uiState.recipesList

    when {
        uiState.isLoading -> LoadingScreen()

        uiState.error != null -> ErrorScreen("Рецепты не удалось загрузить")

        recipes.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Рецептов в этой категории нету",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        else -> {
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = modifier.fillMaxSize()
            ) {

                ScreenHeader(
                    image = uiState.categoryImageUrl,
                    imageContentDescription = "Рецепты категории",
                    text = uiState.categoryTitle
                )

                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(recipes, key = { it.id }) { recipe ->
                        RecipeItem(
                            recipe = recipe,
                            onRecipeClick = {
                                onRecipeClick(recipe.id)
                            },
                        )
                    }
                }
            }
        }
    }
}