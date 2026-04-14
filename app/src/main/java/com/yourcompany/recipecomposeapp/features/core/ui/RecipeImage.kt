package com.yourcompany.recipecomposeapp.features.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.yourcompany.recipecomposeapp.features.core.ui.components.ErrorScreen
import com.yourcompany.recipecomposeapp.features.core.ui.components.LoadingScreen

@Composable
fun TitleImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier,
    contentScale: ContentScale,
) {
    val context = LocalContext.current
    SubcomposeAsyncImage(
        model = remember { ImageRequest.Builder(context) }
            .data(imageUrl)
            .crossfade(true)
            .build(),
        loading = {
            LoadingScreen()
        },
        error = {
            ErrorScreen("Произошла неизвестная ошибка")
        },
        success = {
            Image(
                painter = this.painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
        },
        contentScale = contentScale,
        contentDescription = contentDescription,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .aspectRatio(1.2f)
    )
}