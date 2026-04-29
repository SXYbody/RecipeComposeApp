package com.yourcompany.recipecomposeapp.app.di

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepository
import com.yourcompany.recipecomposeapp.features.categories.presentation.CategoriesViewModel
import com.yourcompany.recipecomposeapp.features.details.presentation.RecipeDetailsViewModel
import com.yourcompany.recipecomposeapp.features.favorites.presentation.FavoritesViewModel
import com.yourcompany.recipecomposeapp.features.recipes.presentation.RecipesViewModel

interface Factory<T> {
    fun create(): T
}

class CategoriesViewModelFactory(
    private val repository: RecipesRepository
) : Factory<CategoriesViewModel> {

    override fun create(): CategoriesViewModel {
        return CategoriesViewModel(repository)
    }
}

class RecipesViewModelFactory(
    private val savedStateHandle: SavedStateHandle,
    private val repository: RecipesRepository
) : Factory<RecipesViewModel> {

    override fun create(): RecipesViewModel {
        return RecipesViewModel(savedStateHandle, repository)
    }
}

class FavoritesViewModelFactory(
    private val application: Application,
    private val repository: RecipesRepository
) : Factory<FavoritesViewModel> {

    override fun create(): FavoritesViewModel {
        return FavoritesViewModel(application, repository)
    }
}

class RecipeDetailsViewModelFactory(
    private val application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val repository: RecipesRepository
) : Factory<RecipeDetailsViewModel> {

    override fun create(): RecipeDetailsViewModel {
        return RecipeDetailsViewModel(application, savedStateHandle, repository)
    }
}