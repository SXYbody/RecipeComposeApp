package com.yourcompany.recipecomposeapp.features.favorites.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepository
import com.yourcompany.recipecomposeapp.features.core.utils.AppDataStoreManager
import com.yourcompany.recipecomposeapp.features.favorites.presentation.model.FavoritesUiState
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val dataStoreManager: AppDataStoreManager,
    private val repository: RecipesRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { currentState -> currentState.copy(isLoading = true) }
        dataStoreManager.getFavoriteIdsFlow()
            .onEach { ids ->
                val recipes = coroutineScope {
                    ids.map { id ->
                        async {
                            repository.getRecipe(id.toInt()).first()?.toUiModel()
                        }
                    }.awaitAll()
                }.filterNotNull()
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