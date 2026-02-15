package com.yourcompany.recipecomposeapp.ui.recipes

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.yourcompany.recipecomposeapp.ui.components.ScreenHeader
import com.yourcompany.recipecomposeapp.ui.recipes.model.RecipeUiModel
import com.yourcompany.recipecomposeapp.ui.recipes.model.toUiModel

@Composable
fun RecipesScreen(
    categoryId: Int,
    modifier: Modifier = Modifier,
    onRecipeClick: (Int) -> Unit,
    titleRecipeScreen: String,
) {
    var recipes by remember { mutableStateOf<List<RecipeUiModel>>(emptyList()) }

    LaunchedEffect(categoryId) {
        categoryId.let {
            recipes =
                RecipesRepositoryStub.getRecipesByCategoryId(it).map { dto -> dto.toUiModel() }
        }
    }

    ScreenHeader(
        painter = painterResource(R.drawable.ic_launcher_foreground),
        painterContent = "Рецепты категории",
        text = titleRecipeScreen
    )

    LazyColumn() {
        items(recipes, key = { it.id }) { recipe ->
            RecipeItem(
                recipe = recipe,
                onRecipeClick = {
                    onRecipeClick(recipe.id)
                }
            )
        }
    }
}