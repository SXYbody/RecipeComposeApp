package com.yourcompany.recipecomposeapp.features.favorites.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.yourcompany.recipecomposeapp.features.core.utils.AppDataStoreManager
import com.yourcompany.recipecomposeapp.features.favorites.presentation.model.FavoritesUiState
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class FavoritesViewModel(
    application: Application,
) : AndroidViewModel(application = application) {

    private val favoriteDataStoreManager = AppDataStoreManager(application.applicationContext)
    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { currentState -> currentState.copy(isLoading = true) }
        favoriteDataStoreManager.getFavoriteIdsFlow()
            .onEach { ids ->
                val recipes = ids.mapNotNull {
                    RecipesRepositoryStub.getRecipeById(it.toInt())?.toUiModel()
                }
                _uiState.update {
                    it.copy(recipes = recipes, isLoading = false)
                }
            }
            .catch { error ->
                _uiState.update {
                    it.copy(isLoading = false, error = error.message)
                }
            }
            .launchIn(viewModelScope)
    }
}