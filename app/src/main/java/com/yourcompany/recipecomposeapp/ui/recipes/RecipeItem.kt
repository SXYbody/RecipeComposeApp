package com.yourcompany.recipecomposeapp.ui.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.ui.recipes.model.RecipeUiModel

@Composable
fun RecipeItem(
    recipe: RecipeUiModel,
    onRecipeClick: (Int) -> Unit,
) {
    Card(
        onClick = { onRecipeClick(recipe.id) }
    ) {
        Column {
            AsyncImage(
                model = recipe.imageUrl,
                placeholder = painterResource(R.drawable.ic_launcher_background),
                error = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = recipe.title,
            )
            Text(text = recipe.title, style = MaterialTheme.typography.titleMedium)
        }
    }
}
