package com.yourcompany.recipecomposeapp.features.categories.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.yourcompany.recipecomposeapp.features.core.ui.TitleImage

@Composable
fun CategoryItem(
    onClick: () -> Unit,
    image: String,
    modifier: Modifier = Modifier,
    imageContentDescription: String,
    title: String,
    description: String,
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            TitleImage(
                imageUrl = image,
                contentDescription = imageContentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3,
                minLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 8.dp)
            )
        }
    }
}