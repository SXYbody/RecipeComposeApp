package com.yourcompany.recipecomposeapp.features.details.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepository
import com.yourcompany.recipecomposeapp.features.core.utils.AppDataStoreManager
import com.yourcompany.recipecomposeapp.features.details.presentation.model.RecipeDetailsUiState
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle,
    private val repository: RecipesRepository,
) : AndroidViewModel(application = application) {
    private val recipeId: Int = checkNotNull(savedStateHandle["recipeId"])

    //    private val recipe: RecipeUiModel = checkNotNull(
//        RecipesRepositoryStub.getRecipeById(recipeId)?.toUiModel()
//    )
    private val favoriteDataStoreManager = AppDataStoreManager(application.applicationContext)

    private val _uiState = MutableStateFlow(RecipeDetailsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.getRecipe(recipeId).collect { recipeDto ->
                if (recipeDto != null) {
                    val recipe: RecipeUiModel = recipeDto.toUiModel()
                    _uiState.update { it.copy(recipe = recipe) }

                    favoriteDataStoreManager.isFavoriteFlow(recipe.id).onEach { isFavorite ->
                        _uiState.update {
                            it.copy(
                                isFavoriteSave = isFavorite,
                                isLoading = false
                            )
                        }
                    }.catch { error ->
                        _uiState.update {
                            it.copy(isLoading = false, error = error.message)
                        }
                    }.launchIn(viewModelScope)
                } else {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
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
            _uiState.value.recipe?.id?.let {
                if (favoriteDataStoreManager.isFavorite(it)) {
                    favoriteDataStoreManager.removeFavorite(
                        it
                    )
                } else favoriteDataStoreManager.addFavorite(it)
            }
        }
    }
}
