package com.yourcompany.recipecomposeapp.ui.details

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.yourcompany.recipecomposeapp.data.model.IngredientDto
import kotlin.math.roundToInt

@Composable
fun IngredientItem(
    title: String,
    quantity: Double,
    unitOfMeasure: String,
    modifier: Modifier = Modifier
) {
    val measureText = when {
        quantity >= 1 -> quantity
        quantity >= 0.75 -> "3/4"
        quantity >= 0.5 -> "1/2"
        quantity >= 0.25 -> "1/4"
        else -> "щепотка"
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(10.dp),

            )
        Text(
            text = " $measureText $unitOfMeasure",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(10.dp),
            minLines = 1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun PortionsSlider(
    currentPortions: Int,
    onPortionsChange: (Int) -> Unit,
) {
    Slider(
        value = currentPortions.toFloat(),
        onValueChange = { onPortionsChange(it.roundToInt()) },
        valueRange = 1f..12f,
        modifier = Modifier.padding(horizontal = 16.dp),
        colors = SliderDefaults.colors(
            thumbColor = MaterialTheme.colorScheme.tertiary,
            activeTrackColor = MaterialTheme.colorScheme.tertiaryContainer,
            inactiveTrackColor = MaterialTheme.colorScheme.tertiaryContainer
        ),
    )
}

@Composable
fun IngredientsList(
    list: List<IngredientDto>
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        list.forEachIndexed { index, ingredient ->
            IngredientItem(
                title = ingredient.description,
                quantity = ingredient.quantity,
                unitOfMeasure = ingredient.unitOfMeasure,
            )
            if (index < list.lastIndex) HorizontalDivider(color = MaterialTheme.colorScheme.outline)
        }
    }
}

@Composable
fun MethodList(
    list: List<String>
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 50.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        list.forEachIndexed { index, item ->
            Text(
                text = item,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(10.dp)
            )
            if (index < list.lastIndex) HorizontalDivider(color = MaterialTheme.colorScheme.outline)
        }
    }
}

