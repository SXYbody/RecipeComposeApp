package com.yourcompany.recipecomposeapp.features.recipes.presentation

import androidx.lifecycle.SavedStateHandle
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class RecipesViewModelTest {

    private val repository = mockk<RecipesRepository>()
    private lateinit var viewModel: RecipesViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    private fun createViewModel(
        categoryId: Int = 1,
        categoryTitle: String = "Завтраки",
        categoryImageUrl: String = "http://example.com/image.jpg"
    ): RecipesViewModel {
        val savedStateHandle = SavedStateHandle(
            mapOf(
                "categoryId" to categoryId,
                "categoryTitle" to categoryTitle,
                "categoryImageUrl" to categoryImageUrl
            )
        )
        return RecipesViewModel(savedStateHandle, repository)
    }

    @Test
    fun `loads recipes for category`() {
        every { repository.getRecipesByCategory(1) } returns flowOf(emptyList())
        viewModel = createViewModel()

        val state = viewModel.uiState.value
        assertEquals("Завтраки", state.categoryTitle)
        assertTrue(state.recipesList.isEmpty())
        assertFalse(state.isLoading)
    }

    @Test
    fun `state reflects category title from savedState`() {
        every { repository.getRecipesByCategory(1) } returns flowOf(emptyList())
        viewModel = createViewModel(categoryTitle = "Завтраки")

        val state = viewModel.uiState.value
        assertEquals("Завтраки", state.categoryTitle)
    }

    @Test
    fun `shows error when repository throws`() {
        every { repository.getRecipesByCategory(1) } throws IOException("Network error")
        viewModel = createViewModel()

        val state = viewModel.uiState.value
        assertNotNull(state.error)
    }
}
