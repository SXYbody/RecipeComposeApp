package com.yourcompany.recipecomposeapp.features.recipes.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.RecipesUiState
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.URLDecoder

class RecipesViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val categoryId: Int =
        checkNotNull(savedStateHandle["categoryId"])

    private val categoryTitle: String =
        checkNotNull(savedStateHandle["categoryTitle"])

    private val categoryImageUrl: String =
        URLDecoder.decode(checkNotNull(savedStateHandle["categoryImageUrl"]), "UTF-8")

    private val _uiState: MutableStateFlow<RecipesUiState> = MutableStateFlow(RecipesUiState())
    val uiState: StateFlow<RecipesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { currentState -> currentState.copy(isLoading = true) }
            try {

                _uiState.update {
                    it.copy(
                        categoryTitle = categoryTitle,
                        categoryImageUrl = categoryImageUrl,
                        recipesList = RecipesRepositoryStub.getRecipesByCategoryId(categoryId)
                            .map { it.toUiModel() },
                        isLoading = false,
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Произошла неизвестная ошибка"
                    )
                }
            }
        }
    }
}