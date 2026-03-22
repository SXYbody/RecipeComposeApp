package com.yourcompany.recipecomposeapp.features.recipes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.yourcompany.recipecomposeapp.features.core.ui.components.ScreenHeader
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.toUiModel

@Composable
fun RecipesScreen(
    categoryId: Int,
    modifier: Modifier = Modifier,
    onRecipeClick: (Int, RecipeUiModel) -> Unit,
    titleRecipeScreen: String,
) {
    var recipes by remember { mutableStateOf<List<RecipeUiModel>>(emptyList()) }
    val category = RecipesRepositoryStub.getCategoryByCategoryId(categoryId)

    LaunchedEffect(categoryId) {
        categoryId.let {
            recipes =
                RecipesRepositoryStub.getRecipesByCategoryId(it).map { dto -> dto.toUiModel() }
        }
    }

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = modifier.fillMaxSize()
    ) {
        category?.let {
            ScreenHeader(
                image = it.imageUrl,
                imageContentDescription = "Рецепты категории",
                text = titleRecipeScreen
            )
        }

        LazyColumn(
            modifier = modifier.fillMaxSize().weight(1f),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(recipes, key = { it.id }) { recipe ->
                RecipeItem(
                    recipe = recipe,
                    onRecipeClick = {
                        onRecipeClick(recipe.id, recipe)
                    },
                )
            }
        }
    }
}