package com.yourcompany.recipecomposeapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourcompany.recipecomposeapp.R

@Composable
fun ScreenHeader(
    painter: Painter,
    painterContent: String,
    text: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Image(
            painter = painter,
            contentDescription = painterContent,
            contentScale = ContentScale.Crop
        )
        Surface(
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = text)
        }
    }
}

@Preview
@Composable
fun ScreenHeaderPreview() {
    ScreenHeader(
        painter = painterResource(R.drawable.bcg_favorites),
        painterContent = "Бургер",
        text = "КАТЕГОРИИ",
    )
}