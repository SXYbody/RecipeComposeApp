package com.yourcompany.recipecomposeapp.ui.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.yourcompany.recipecomposeapp.ui.categories.model.toUiModel
import com.yourcompany.recipecomposeapp.ui.components.ScreenHeader

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    onClickCategory: (Int) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = modifier
    ) {
        ScreenHeader(
            painter = painterResource(R.drawable.bcg_categories),
            painterContent = "Заголовок категорий",
            text = "КАТЕГОРИИ",
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier.fillMaxSize(),

            ) {
            items(
                items = RecipesRepositoryStub.getCategories().map { it.toUiModel() },
                key = { it.id }) { category ->
                CategoryItem(
                    onClick = {
                        onClickCategory(category.id)
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