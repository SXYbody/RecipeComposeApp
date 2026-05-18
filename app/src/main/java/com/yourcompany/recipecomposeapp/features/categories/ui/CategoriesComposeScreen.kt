package com.yourcompany.recipecomposeapp.features.categories.ui


import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.hasTestTag

class CategoriesComposeScreen(semanticsProvider: SemanticsNodeInteractionsProvider) :
    ComposeScreen<CategoriesComposeScreen>(
        semanticsProvider = semanticsProvider,
        viewBuilderAction = { hasTestTag("categories_screen") }
    ) {
    val loadingIndicator: KNode = child { hasTestTag("loading_indicator") }
    val categoriesGrid: KNode = child { hasTestTag("categories_grid") }
    val categoryItem: KNode = child { hasTestTag("category_item") }
}