package com.yourcompany.recipecomposeapp

object Constants {
    const val ASSETS_URI_PREFIX = "file:///android_asset/"
    const val KEY_RECIPE_OBJECT = "recipe"

    const val CATEGORY_ID = "categoryId"
    const val CATEGORY_TITLE = "categoryTitle"
    const val CATEGORY_IMAGE_URL = "categoryImageUrl"

    object Routes {
        const val PARAM_RECIPE_ID = "recipeId"
        const val RECIPE_DETAILS = "recipe/{$PARAM_RECIPE_ID}"
    }

    object DeepLinks {
        const val DEEP_LINK_SCHEME = "recipeapp"
        const val DEEP_LINK_BASE_URL = "https://recipes.androidsprint.ru"

        fun createRecipeDeepLink(recipeId: Int): String {
            return "$DEEP_LINK_BASE_URL/recipe/$recipeId"
        }
    }
}