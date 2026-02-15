package com.yourcompany.recipecomposeapp.ui.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.ui.components.ScreenHeader
import com.yourcompany.recipecomposeapp.ui.recipes.RecipesScreen

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
){
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = modifier
    ) {
        ScreenHeader(
            painter = painterResource(R.drawable.bcg_favorites),
            painterContent = "Заголовок категорий",
            text = "ИЗБРАННОЕ",
        )

    }
}