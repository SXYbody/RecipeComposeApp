package com.yourcompany.recipecomposeapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yourcompany.recipecomposeapp.data.database.convert.Converters
import com.yourcompany.recipecomposeapp.data.database.dao.CategoryDao
import com.yourcompany.recipecomposeapp.data.database.dao.RecipeDao
import com.yourcompany.recipecomposeapp.data.database.entity.CategoryEntity
import com.yourcompany.recipecomposeapp.data.database.entity.RecipeEntity
@TypeConverters(Converters::class)
@Database(
    entities = [CategoryEntity::class, RecipeEntity::class],
    version = 2,
    exportSchema = false
)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipesDatabase? = null

        fun getDatabase(context: Context): RecipesDatabase {
            return INSTANCE ?: buildDataBase(context).also { INSTANCE = it }
        }
        private fun buildDataBase(context: Context): RecipesDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                RecipesDatabase::class.java,
                "recipes_database"
            ).fallbackToDestructiveMigration().build()
        }
    }
}