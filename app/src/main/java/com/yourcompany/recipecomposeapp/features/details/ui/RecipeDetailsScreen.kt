package com.yourcompany.recipecomposeapp.features.details.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepository
import com.yourcompany.recipecomposeapp.features.core.ui.components.ErrorScreen
import com.yourcompany.recipecomposeapp.features.core.ui.components.LoadingScreen
import com.yourcompany.recipecomposeapp.features.core.ui.components.ScreenHeader
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.yourcompany.recipecomposeapp.features.core.utils.shareRecipe
import com.yourcompany.recipecomposeapp.features.details.presentation.RecipeDetailsViewModel
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.IngredientUiModel

@Composable
fun RecipeDetailsScreen(
    viewModel: RecipeDetailsViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isLoading -> LoadingScreen()

        uiState.error != null -> ErrorScreen("Не удалось загрузить рецепт")

        uiState.recipe == null -> ErrorScreen("Не удалось загрузить рецепт")

        else -> {
            val recipe: RecipeUiModel = requireNotNull(uiState.recipe)
            val currentPortions: Int = uiState.currentPortions
            val scaledIngredients: List<IngredientUiModel> =
                requireNotNull(uiState.scaledIngredients)
            val isFavoriteSave: Boolean = uiState.isFavoriteSave

            val portionsText = pluralStringResource(
                R.plurals.portions_count,
                currentPortions,
                currentPortions
            )
            val context = LocalContext.current

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .windowInsetsPadding(WindowInsets.navigationBars),
            ) {
                ScreenHeader(
                    image = recipe.imageUrl,
                    imageContentDescription = "Заголовок",
                    text = recipe.title,
                    showShareButton = true,
                    onShareClick = { shareRecipe(context, recipe.id, recipe.title) },
                    showFavoriteButton = true,
                    isFavorite = isFavoriteSave,
                    onFavoriteClick = {
                        viewModel.toggleFavorite()
                    },
                )

                Text(
                    text = "ИНГРЕДИЕНТЫ",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    fontSize = 25.sp
                )

                Text(
                    text = portionsText,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                PortionsSlider(
                    currentPortions = currentPortions,
                    onPortionsChange = { newValue ->
                        viewModel.updatePortions(newValue)
                    },
                )

                IngredientsList(scaledIngredients)

                Text(
                    text = "СПОСОБ ПРИГОТОВЛЕНИЯ",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    fontSize = 25.sp
                )

                MethodList(recipe.method)
            }
        }
    }
}