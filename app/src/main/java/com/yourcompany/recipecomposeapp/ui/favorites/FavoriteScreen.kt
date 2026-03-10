package com.yourcompany.recipecomposeapp.ui.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.yourcompany.recipecomposeapp.ui.components.ScreenHeader
import com.yourcompany.recipecomposeapp.ui.recipes.RecipeItem
import com.yourcompany.recipecomposeapp.ui.recipes.model.RecipeUiModel
import com.yourcompany.recipecomposeapp.ui.utils.AppDataStoreManager
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    onClickRecipe: (Int) -> Unit,
) {
    val content = LocalContext.current
    val appData = remember { AppDataStoreManager(content) }
    val favoriteSet: MutableList<RecipeUiModel> = remember { mutableStateListOf() }

    LaunchedEffect(Unit) {
        favoriteSet.addAll(appData.getAllFavoriteToModels())
    }

    Column {
        ScreenHeader(
            image = "file:///android_asset/favorites.png",
            imageContentDescription = "Заголовок категорий",
            text = "ИЗБРАННОЕ",
        )

        if (favoriteSet.isEmpty()) {
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
                items = favoriteSet,
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