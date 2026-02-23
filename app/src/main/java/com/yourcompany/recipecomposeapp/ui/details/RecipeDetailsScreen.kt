package com.yourcompany.recipecomposeapp.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Preview
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.ui.components.ScreenHeader
import com.yourcompany.recipecomposeapp.ui.recipes.model.RecipeUiModel
import com.yourcompany.recipecomposeapp.ui.utils.shareRecipe

@Composable
fun RecipeDetailsScreen(
    recipe: RecipeUiModel? = null,
    isFavorite: Boolean = false,
    onFavoriteToggle: (Boolean) -> Unit = {},
) {
    if (recipe == null) {
        Text("рецепт не найден!")
        return
    }

    var currentPortions by rememberSaveable { mutableIntStateOf(recipe.servings) }
    val scaledIngredients = remember(currentPortions, recipe.ingredients) {
        val multiplier = currentPortions.toDouble() / recipe.servings
        recipe.ingredients.map { ingredient ->
            ingredient.copy(
                quantity = ingredient.quantity * multiplier
            )
        }
    }
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
    ) {
        ScreenHeader(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            painterContent = "Заголовок",
            text = recipe.title,
            showShareButton = true,
            onShareClick = { shareRecipe(context, recipe.id, recipe.title) },
            showFavoriteButton = true,
            isFavorite = isFavorite,
            onFavoriteClick = { onFavoriteToggle },
        )

        PortionsSlider(
            currentPortions = currentPortions,
            onPortionsChange = { newValue ->
                currentPortions = newValue
            }
        )
        Text(portionsText)

        IngredientsList(scaledIngredients)

        Text(text = recipe.method)
    }
}

@Preview
@Composable
fun RecipeDetailsScreenPreview() {
    RecipeDetailsScreen()
}