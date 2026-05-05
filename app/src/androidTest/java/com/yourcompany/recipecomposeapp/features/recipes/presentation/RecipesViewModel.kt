package com.yourcompany.recipecomposeapp.features.recipes.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepository
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.RecipesUiState
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: RecipesRepository
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
        loadRecipes(categoryId)
    }

    fun loadRecipes(categoryId: Int) {
        viewModelScope.launch {
            _uiState.update { currentState -> currentState.copy(isLoading = true) }
            repository.getRecipesByCategory(categoryId).collect { dto ->
                val recipesList = dto.map { it.toUiModel() }
                _uiState.update { currentState ->
                    currentState.copy(
                        categoryTitle = categoryTitle,
                        categoryImageUrl = categoryImageUrl,
                        recipesList = recipesList,
                        isLoading = false,
                    )
                }
            }
        }
    }
}