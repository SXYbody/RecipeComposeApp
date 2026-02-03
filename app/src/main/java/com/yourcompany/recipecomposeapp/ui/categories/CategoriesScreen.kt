package com.yourcompany.recipecomposeapp.ui.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.ui.categories.components.ScreenHeader

@Composable
fun CategoriesScreen(){
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
    ) {
        ScreenHeader(
            painter = painterResource(R.drawable.bcg_categories),
            painterContent = "Заголовок категорий",
            text = "КАТЕГОРИИ",
        )
    }
}