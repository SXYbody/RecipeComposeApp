package com.yourcompany.recipecomposeapp.ui.categories.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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
            modifier = Modifier.fillMaxSize()
        )
        Text(
            text = text, modifier = Modifier
                .align(Alignment.BottomStart)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(corner = CornerSize(10.dp))
                )
                .padding(8.dp)
        )
    }
}

@Preview
@Composable
fun ScreenHeaderPreview() {
    ScreenHeader(
        painter = painterResource(R.drawable.bcg_categories),
        painterContent = "Бургер",
        text = "КАТЕГОРИИ",
    )
}