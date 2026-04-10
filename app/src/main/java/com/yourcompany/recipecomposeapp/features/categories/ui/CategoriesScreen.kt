package com.yourcompany.recipecomposeapp.features.categories.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepository
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.yourcompany.recipecomposeapp.features.categories.presentation.CategoriesViewModel
import com.yourcompany.recipecomposeapp.features.categories.presentation.model.toUiModel
import com.yourcompany.recipecomposeapp.features.core.ui.components.ErrorScreen
import com.yourcompany.recipecomposeapp.features.core.ui.components.LoadingScreen
import com.yourcompany.recipecomposeapp.features.core.ui.components.ScreenHeader

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    onClickCategory: (Int, String, String) -> Unit,
    repository: RecipesRepository
) {
    val viewModel = remember { CategoriesViewModel(recipeRepository = repository) }
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