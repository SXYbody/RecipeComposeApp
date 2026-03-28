package com.yourcompany.recipecomposeapp.features.details.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.yourcompany.recipecomposeapp.features.core.utils.AppDataStoreManager
import com.yourcompany.recipecomposeapp.features.details.presentation.model.RecipeDetailsUiState
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle,
) : AndroidViewModel(application = application) {
    private val recipeId: Int = checkNotNull(savedStateHandle["recipeId"])
    private val recipe: RecipeUiModel = checkNotNull(
        RecipesRepositoryStub.getRecipeById(recipeId)?.toUiModel()
    )
    private val favoriteDataStoreManager = AppDataStoreManager(application.applicationContext)

    private val _uiState = MutableStateFlow(RecipeDetailsUiState(recipe))
    val uiState = _uiState.asStateFlow()

    init {
        favoriteDataStoreManager.isFavoriteFlow(recipe.id).onEach { isFavorite ->
            _uiState.update {
                it.copy(
                    isFavoriteSave = isFavorite,
                )
            }
        }.launchIn(viewModelScope)

    }

    fun updatePortions(portions: Int) {
        _uiState.update {
            it.copy(
                currentPortions = portions,
            )
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            if (favoriteDataStoreManager.isFavorite(recipe.id)) {
                favoriteDataStoreManager.removeFavorite(
                    recipe.id
                )
            } else favoriteDataStoreManager.addFavorite(recipe.id)
        }
    }
}