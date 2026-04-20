package com.yourcompany.recipecomposeapp.features.categories.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepository
import com.yourcompany.recipecomposeapp.features.categories.presentation.model.CategoriesUiState
import com.yourcompany.recipecomposeapp.features.categories.presentation.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val recipeRepository: RecipesRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<CategoriesUiState> =
        MutableStateFlow(CategoriesUiState())
    val uiState: StateFlow<CategoriesUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _uiState.update { currentState -> currentState.copy(isLoading = true) }
            recipeRepository.getCategories().collect {
                val categoriesList = it.map { recipe -> recipe.toUiModel() }
                _uiState.update { uiState ->
                    uiState.copy(
                        categories = categoriesList,
                        isLoading = false
                    )
                }
            }
        }
    }
}