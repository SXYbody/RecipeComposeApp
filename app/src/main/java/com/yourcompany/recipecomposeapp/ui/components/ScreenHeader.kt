package com.yourcompany.recipecomposeapp.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

import com.yourcompany.recipecomposeapp.R

@Composable
fun ScreenHeader(
    image: String,
    imageContentDescription: String,
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
        AsyncImage(
            model = image,
            placeholder = painterResource(R.drawable.ic_launcher_background),
            error = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = imageContentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
        ) {
            if (showShareButton) {
                Button(
                    onClick = onShareClick,
                ) { Text("Поделиться") }
            }

            if (showFavoriteButton) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.3f))
                        .clickable(onClick = onFavoriteClick),
                    contentAlignment = Alignment.Center
                ) {
                    Crossfade(
                        targetState = isFavorite,
                        animationSpec = tween(durationMillis = 300),
                        label = "favorite_animation"
                    ) { isCurrentFavorite ->
                        val heartPainter = rememberVectorPainter(
                            image = ImageVector.vectorResource(
                                id = if (isCurrentFavorite) R.drawable.ic_heart
                                else R.drawable.ic_heart_empty
                            )
                        )
                        Icon(
                            painter = heartPainter,
                            contentDescription = "Избранное",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }

        Surface(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 25.sp,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }
    }
}