package com.yourcompany.recipecomposeapp.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.ui.components.ScreenHeader
import com.yourcompany.recipecomposeapp.ui.recipes.model.RecipeUiModel
import com.yourcompany.recipecomposeapp.ui.utils.FavoritePrefsManager
import com.yourcompany.recipecomposeapp.ui.utils.shareRecipe

@Composable
fun RecipeDetailsScreen(
    recipe: RecipeUiModel,
    favoritePrefs: FavoritePrefsManager,
) {
    var currentPortions by rememberSaveable { mutableIntStateOf(recipe.servings) }
    val scaledIngredients = remember(currentPortions, recipe.ingredients) {
        val multiplier = currentPortions.toDouble() / recipe.servings
        recipe.ingredients.map { ingredient ->
            ingredient.copy(
                quantity = ingredient.quantity * multiplier
            )
        }
    }
    var isFavoriteSave by rememberSaveable { mutableStateOf(favoritePrefs.isFavorite(recipe.id)) }

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
                isFavoriteSave = !isFavoriteSave
                if (isFavoriteSave) favoritePrefs.addToFavorite(recipe.id)
                else favoritePrefs.removeToFavorite(recipe.id)
            },
        )

        Text(
            text = "ИНГРЕДИЕНТЫ",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(10.dp),
            fontSize = 25.sp
        )

        Text(
            text = portionsText,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 10.dp)
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
                .padding(10.dp),
            fontSize = 25.sp
        )

        MethodList(recipe.method)
    }
}