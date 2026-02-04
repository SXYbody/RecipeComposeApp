package com.yourcompany.recipecomposeapp.ui.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.ui.categories.components.ScreenHeader

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
){
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = modifier
    ) {
        ScreenHeader(
            painter = painterResource(R.drawable.bcg_categories),
            painterContent = "Заголовок категорий",
            text = "КАТЕГОРИИ",
        )
    }
}