package com.yourcompany.recipecomposeapp.features.categories.presentation

import app.cash.turbine.test
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepository
import com.yourcompany.recipecomposeapp.features.categories.presentation.CategoriesViewModel
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class CategoriesViewModelTest {

    private val repository = mockk<RecipesRepository>()
    private lateinit var viewModel: CategoriesViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        every { repository.getCategories() } returns flowOf(emptyList())
        viewModel = CategoriesViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }


    @Test
    fun `load repositories from store`() = runTest {
        val state = viewModel.uiState.value

        assertFalse(state.isLoading)
        assertEquals(0, state.categories.size)
    }

    @Test
    fun `shows empty list when repository returns no data`() = runTest {
        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state.categories.isEmpty())
            assertNull(state.error)
            assertFalse(state.isLoading)
        }
    }

    @Test
    fun `shows error when repository throws`() = runTest {
        val errorRepository = mockk<RecipesRepository>()
        every { errorRepository.getCategories() } throws IOException("Network error")

        val errorViewModel = CategoriesViewModel(errorRepository)

        val state = errorViewModel.uiState.value
        assertNotNull(state.error)
    }
}