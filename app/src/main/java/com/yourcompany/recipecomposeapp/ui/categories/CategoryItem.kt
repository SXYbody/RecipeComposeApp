package com.yourcompany.recipecomposeapp.ui.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.yourcompany.recipecomposeapp.R

@Composable
fun CategoryItem(
    onClick: () -> Unit,
    image: String,
    imageContentDescription: String,
    title: String,
    description: String,
    titleStyle: TextStyle = MaterialTheme.typography.titleMedium,
    descriptionStyle: TextStyle = MaterialTheme.typography.labelLarge
){
    Card(
        onClick = onClick,
    ) {
        Column {
            AsyncImage(
                model = image,
                placeholder = painterResource(R.drawable.ic_launcher_background),
                error = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = imageContentDescription,
            )
            Text(text = title, style = titleStyle)

            Text(text = description, style = descriptionStyle)
        }
    }
}

@Preview
@Composable
fun CategoryItemPreview(){
    CategoryItem(
        {},
        "",
        "",
        "Всем привет!",
        "Это Бильбо..."
    )
}