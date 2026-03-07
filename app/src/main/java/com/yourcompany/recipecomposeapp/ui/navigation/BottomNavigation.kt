package com.yourcompany.recipecomposeapp.ui.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigation(
    onCategoriesClick: () -> Unit,
    onFavoriteClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Button(
            modifier = Modifier.weight(1f),
            onClick = onCategoriesClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "КАТЕГОРИИ",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            modifier = Modifier.weight(1f),
            onClick = onFavoriteClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "ИЗБРАННОЕ",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}