package com.yourcompany.recipecomposeapp.ui.details

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.ui.components.ScreenHeader
import com.yourcompany.recipecomposeapp.ui.recipes.model.RecipeUiModel
import com.yourcompany.recipecomposeapp.ui.utils.AppDataStoreManager
import com.yourcompany.recipecomposeapp.ui.utils.shareRecipe
import kotlinx.coroutines.launch

@Composable
fun RecipeDetailsScreen(
    recipe: RecipeUiModel,
    appData: AppDataStoreManager,
) {
    val coroutineScope = rememberCoroutineScope()
    var currentPortions by rememberSaveable { mutableIntStateOf(recipe.servings) }
    val scaledIngredients = remember(currentPortions, recipe.ingredients) {
        val multiplier = currentPortions.toDouble() / recipe.servings
        recipe.ingredients.map { ingredient ->
            ingredient.copy(
                quantity = ingredient.quantity * multiplier
            )
        }
    }
    val isFavoriteSave: Boolean by appData.isFavoriteFlow(recipe.id).collectAsState(initial = false)

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
                coroutineScope.launch {
                    if (isFavoriteSave) appData.removeFavorite(recipe.id)
                    else appData.addFavorite(recipe.id)
                }
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
                currentPortions = newValue
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