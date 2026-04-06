package com.yourcompany.recipecomposeapp

import android.app.DownloadManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yourcompany.recipecomposeapp.data.model.CategoryDto
import com.yourcompany.recipecomposeapp.data.model.RecipeDto
import com.yourcompany.recipecomposeapp.theme.RecipeComposeAppTheme
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {
    private var deepLinkIntent by mutableStateOf<Intent?>(null)
    private val threadPool: ExecutorService = Executors.newFixedThreadPool(10)
    private val okHttpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        intent?.data?.let { _ ->
            deepLinkIntent = intent
        }

        setContent {
            RecipesApp(
                intent = deepLinkIntent
            )
        }

        Log.e("Pool", "Метод onCreate() выполняется на потоке: ${Thread.currentThread().name}")

        thread {
            val request = Request.Builder()
                .url("https://recipes.androidsprint.ru/api/category")
                .build()

            okHttpClient.newCall(request).execute().use { response ->
                val body = response.body.string()
                Log.e("Pool", body)
                val categories: List<CategoryDto> = Json.decodeFromString<List<CategoryDto>>(body)

                categories.forEach { category ->
                    threadPool.execute {
                        val request = Request.Builder()
                            .url("https://recipes.androidsprint.ru/api/category/${category.id}/recipes")
                            .build()

                        okHttpClient.newCall(request).execute().use { response ->
                            val body = response.body.string()
                            val recipes = Json.decodeFromString<List<RecipeDto>>(body)

                            Log.e(
                                "Pool",
                                "Имя потока: ${Thread.currentThread().name}, " +
                                        "Категория: ${category.title}, Кол-во рецептов: ${recipes.size}"
                            )

                        }
                    }
                }
            }
        }
//
//        threadPool.execute {
//            Log.e("Pool", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
//
//            val url = URL("https://recipes.androidsprint.ru/api/category")
//            var connect: HttpURLConnection? = null
//            try {
//                connect = url.openConnection() as HttpURLConnection
//
//
//                connect.requestMethod = "GET"
//                connect.connect()
//
//                val response = connect.inputStream
//                    .bufferedReader()
//                    .readText()
//                Log.e("Pool", response)
//
//                val categories = Json.decodeFromString<List<CategoryDto>>(response)
//
//                categories.forEach { category ->
//                    threadPool.execute {
//                        var connect: HttpURLConnection? = null
//                        try {
//                            val urlCategory =
//                                URL("https://recipes.androidsprint.ru/api/category/${category.id}/recipes")
//                            connect = urlCategory.openConnection() as HttpURLConnection
//
//                            connect.requestMethod = "GET"
//                            connect.connect()
//
//                            val responseCode = connect.responseCode
//
//                            if (responseCode == HttpURLConnection.HTTP_OK) {
//                                val response = connect.inputStream
//                                    .bufferedReader()
//                                    .readText()
//                                val recipes = Json.decodeFromString<List<RecipeDto>>(response)
//                                Log.e(
//                                    "Pool",
//                                    "Имя потока: ${Thread.currentThread().name}, " +
//                                            "Категория: ${category.title}, Кол-во рецептов: ${recipes.size}"
//                                )
//                            }
//
//                        } catch (e: Exception) {
//                            Log.e("Pool", "Ошибка: ${e::class.simpleName}: ${e.message}")
//                        } finally {
//                            connect?.disconnect()
//                        }
//                    }
//                }
//            } catch (e: Exception) {
//                Log.e("Pool", "Ошибка: ${e::class.simpleName}: ${e.message}")
//            } finally {
//                connect?.disconnect()
//            }
//        }
    }
    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        RecipeComposeAppTheme {
            Greeting("Android")
        }
    }
}