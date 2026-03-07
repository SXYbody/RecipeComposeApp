package com.yourcompany.recipecomposeapp.ui.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.yourcompany.recipecomposeapp.ui.components.ScreenHeader
import com.yourcompany.recipecomposeapp.ui.recipes.RecipeItem
import com.yourcompany.recipecomposeapp.ui.utils.FavoritePrefsManager

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    onClickRecipe: (Int) -> Unit,
) {
    val favoritePref = FavoritePrefsManager(LocalContext.current)

    Column {
        ScreenHeader(
            image = "file:///android_asset/favorites.png",
            imageContentDescription = "Заголовок категорий",
            text = "ИЗБРАННОЕ",
        )

        if (favoritePref.getAllFavoritesToModels().isEmpty()) {
            Text("Вы ещё не добавили ни одного рецепта в избранное")
            return
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                items = favoritePref.getAllFavoritesToModels(),
                key = { it.id }) { recipe ->
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