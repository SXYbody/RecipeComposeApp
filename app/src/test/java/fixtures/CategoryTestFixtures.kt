package fixtures

import com.yourcompany.recipecomposeapp.data.model.CategoryDto

object CategoryTestFixtures {
    fun createCategoryDto(
        id: Int = 1,
        title: String = "Бургеры",
        description: String = "Самые вкусные бургеры на диком западе..",
        imageUrl: String = "burgers.jpg",
    ) = CategoryDto(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl
    )

    fun createCategoryDtoList(
        count: Int = 2
    ) = List(count) { index -> createCategoryDto(id = index + 1, title = "Категория ${index + 1}") }
}