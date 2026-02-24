package com.yourcompany.recipecomposeapp.ui.components


import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.yourcompany.recipecomposeapp.R

@Composable
fun ScreenHeader(
    painter: Painter,
    painterContent: String,
    text: String,
    showShareButton: Boolean = false,
    onShareClick: () -> Unit = {},
    showFavoriteButton: Boolean = false,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {}
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
            if (showShareButton) {
                Button(
                    onClick = onShareClick,
                ) { Text("Поделиться") }
            }

            if (showFavoriteButton) {
                Button(
                    onClick = onFavoriteClick
                ) {
                    Crossfade(
                        targetState = isFavorite,
                        animationSpec = tween(durationMillis = 300),
                        label = "favorite_animation"
                    ) { isCurrentFavorite ->
                        val heartIcon = rememberVectorPainter(
                            image = ImageVector.vectorResource(
                                id = if (isCurrentFavorite) R.drawable.ic_android_black_24dp
                                else R.drawable.ic_android_black_25dp
                            )
                        )
                        Icon(
                            painter = heartIcon,
                            contentDescription = "Favorite",
                            tint = Color.Unspecified
                        )
                    }
                }
            }
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