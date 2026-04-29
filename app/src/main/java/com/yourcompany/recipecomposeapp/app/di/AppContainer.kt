package com.yourcompany.recipecomposeapp.app.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.yourcompany.recipecomposeapp.BuildConfig
import com.yourcompany.recipecomposeapp.data.database.RecipesDatabase
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepositoryImpl
import com.yourcompany.recipecomposeapp.features.core.network.NetworkConfig
import com.yourcompany.recipecomposeapp.features.core.network.api.RecipesApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class AppContainer(context: Context) {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }
    private val okHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS).addInterceptor(loggingInterceptor)
        .build()
    private val contentType = "application/json".toMediaType()
    private val json = Json { ignoreUnknownKeys = true; coerceInputValues = true }
    private val retrofit = Retrofit.Builder()
        .baseUrl(NetworkConfig.BASE_URL)
        .addConverterFactory(json.asConverterFactory(contentType))
        .client(okHttpClient)
        .build()

    private val recipesApi = retrofit.create(RecipesApiService::class.java)
    private val recipesDatabase = RecipesDatabase.getDatabase(context)
    val recipesRepository = RecipesRepositoryImpl(recipesApi, recipesDatabase)

}