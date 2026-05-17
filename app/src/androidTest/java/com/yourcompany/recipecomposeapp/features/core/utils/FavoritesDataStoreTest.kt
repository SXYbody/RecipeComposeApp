package com.yourcompany.recipecomposeapp.features.core.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class FavoritesDataStoreTest {
    private lateinit var context: Context
    private lateinit var manager: AppDataStoreManager

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        manager = AppDataStoreManager(context)
    }

    @After
    fun tearDown() {
        runBlocking { context.dataStore.edit { it.clear() } }
    }

    @Test
    fun addFavoriteSavesRecipeId() = runTest {
        manager.addFavorite(recipeId = 42)
        assertTrue(manager.getFavoriteIdsFlow().first().contains("42"))
    }

    @Test
    fun removeFromFavoritesDeletesRecipeId() = runTest {
        manager.addFavorite(recipeId = 42)
        assertTrue(manager.getFavoriteIdsFlow().first().contains("42"))
        manager.removeFavorite(recipeId = 42)
        assertFalse(manager.getFavoriteIdsFlow().first().contains("42"))
    }

    @Test
    fun favoritesFlowEmitsUpdatesReactively() = runTest {
        manager.getFavoriteIdsFlow().test {
            val initial = awaitItem()
            assertTrue(initial.isEmpty())
            manager.addFavorite(recipeId = 42)

            val updated = awaitItem()
            assertTrue(updated.contains("42"))

            cancelAndIgnoreRemainingEvents()
        }
    }
}