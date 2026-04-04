package com.yourcompany.recipecomposeapp

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
import com.yourcompany.recipecomposeapp.theme.RecipeComposeAppTheme
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : ComponentActivity() {
    private var deepLinkIntent by mutableStateOf<Intent?>(null)

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

        Log.e("", "Метод onCreate() выполняется на потоке: ${Thread.currentThread().name}")

        Thread {
            Log.e("", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
            val url = URL("https://recipes.androidsprint.ru/api/category")
            val connect = url.openConnection() as HttpURLConnection

            try {
                connect.requestMethod = "GET"
                connect.connect()

                val responseCode = connect.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connect.inputStream
                        .bufferedReader()
                        .readText()
                    Log.e("", response)

                    val categories = Json.decodeFromString<List<CategoryDto>>(response)
                    Log.e("", "${categories.size} ${categories.map { it.title }}")
                }
            } catch (e: Exception) {
                Log.e("", "Ошибка", e)
            } finally {
                connect.disconnect()
            }
        }.start()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.let { _ ->
            deepLinkIntent = intent
        }
        setIntent(intent)
    }
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