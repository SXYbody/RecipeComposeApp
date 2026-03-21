package com.yourcompany.recipecomposeapp.ui.favorites

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.yourcompany.recipecomposeapp.ui.components.ScreenHeader
import com.yourcompany.recipecomposeapp.ui.recipes.RecipeItem
import com.yourcompany.recipecomposeapp.ui.recipes.model.RecipeUiModel
import com.yourcompany.recipecomposeapp.ui.recipes.model.toUiModel
import com.yourcompany.recipecomposeapp.ui.utils.AppDataStoreManager
import kotlinx.coroutines.flow.map

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    onClickRecipe: (Int) -> Unit,
    recipesRepository: RecipesRepositoryStub,
    appData: AppDataStoreManager,
) {
    val favoriteListModel = appData.getFavoriteIdsFlow().map { ids ->
        ids.mapNotNull { recipesRepository.getRecipeById(it.toInt())?.toUiModel() }
    }.collectAsState(initial = emptyList())

    Column {
        ScreenHeader(
            image = "file:///android_asset/favorites.png",
            imageContentDescription = "Заголовок категорий",
            text = "ИЗБРАННОЕ",
        )

        if (favoriteListModel.value.isEmpty()) {
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
            return
        }

        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                items = favoriteListModel.value,
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