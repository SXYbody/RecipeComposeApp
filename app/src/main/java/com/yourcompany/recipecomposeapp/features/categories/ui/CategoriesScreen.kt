package com.yourcompany.recipecomposeapp.features.categories.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.yourcompany.recipecomposeapp.app.di.CategoriesViewModelFactory
import com.yourcompany.recipecomposeapp.app.di.RecipeApplication
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepository
import com.yourcompany.recipecomposeapp.features.categories.presentation.CategoriesViewModel
import com.yourcompany.recipecomposeapp.features.core.ui.components.ErrorScreen
import com.yourcompany.recipecomposeapp.features.core.ui.components.LoadingScreen
import com.yourcompany.recipecomposeapp.features.core.ui.components.ScreenHeader

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    onClickCategory: (Int, String, String) -> Unit,
) {
    val appContainer = (LocalContext.current.applicationContext as RecipeApplication).appContainer

    val viewModel = remember { CategoriesViewModelFactory(appContainer.recipesRepository).create() }
    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isLoading -> LoadingScreen()

        uiState.error != null -> ErrorScreen("Категории не удалось загрузить")

        else -> {
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = modifier
            ) {
                ScreenHeader(
                    image = "file:///android_asset/categories.png",
                    imageContentDescription = "Заголовок категорий",
                    text = "КАТЕГОРИИ",
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(
                        items = uiState.categories,
                        key = { it.id }) { category ->
                        CategoryItem(
                            onClick = {
                                onClickCategory(category.id, category.title, category.imageUrl)
                            },
                            image = category.imageUrl,
                            imageContentDescription = "Картинка категории",
                            title = category.title,
                            description = category.description
                        )
                    }
                }
            }
        }
    }
}