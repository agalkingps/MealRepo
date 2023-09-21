package ru.agalkingps.mealapp.repo

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.agalkingps.mealapp.data.model.Meal
import ru.agalkingps.mealapp.data.MealRepositoryInterface

class FakeMealRepository  : MealRepositoryInterface {

    private lateinit var mealList: List<Meal>

    override suspend fun getAllMeals(): Flow<List<Meal>> = flow {
        var list : MutableList<Meal> = mutableListOf()
        mealList.forEach {
            list.add(it)
            delay(100L)
            emit(list)
            list.clear()
        }
    }

    override suspend fun getMealById(mealId: Int):  Meal? {
        mealList.forEach {
            if (it.id == mealId) {
                return it
            }
        }
        return null
    }

    internal fun fillMMealList() {
        mealList = listOf(
            Meal(
                1,
                "Fresh Vegges and Greens",
                "Very awesome list item has very awesome subtitle. This is bit long",
                2.5,
                R.drawable.food1
            ),
            Meal(
                2,
                "Best blue berries",
                "Very awesome list item has very awesome subtitle. This is bit long",
                3.0,
                R.drawable.food2
            ),
            Meal(
                3,
                "Cherries La Bloom",
                "Very awesome list item has very awesome subtitle. This is bit long",
                4.5,
                R.drawable.food3
            ),
            Meal(
                4,
                "Fruits everyday",
                "Very awesome list item has very awesome subtitle. This is bit long",
                3.0,
                R.drawable.food4
            ),
            Meal(
                5,
                "Sweet and sour",
                "Very awesome list item has very awesome subtitle. This is bit long",
                5.0,
                R.drawable.food5
            ),
            Meal(
                6,
                "Pancakes for everyone",
                "Very awesome list item has very awesome subtitle. This is bit long",
                3.5,
                R.drawable.food6
            ),
            Meal(
                7,
                "Cupcakes and sparkle",
                "Very awesome list item has very awesome subtitle. This is bit long",
                3.0,
                R.drawable.food7
            ),
            Meal(
                8,
                "Best Burgers shop",
                "Very awesome list item has very awesome subtitle. This is bit long",
                4.5,
                R.drawable.food8
            ),
            Meal(
                9,
                "Coffee of India",
                "Very awesome list item has very awesome subtitle. This is bit long",
                2.5,
                R.drawable.food9
            ),
            Meal(
                10,
                "Pizza boy town",
                "Very awesome list item has very awesome subtitle. This is bit long",
                4.5,
                R.drawable.food10
            ),
            Meal(
                11,
                "Burgers and Chips",
                "Very awesome list item has very awesome subtitle. This is bit long",
                5.5,
                R.drawable.food11
            ),
            Meal(
                12,
                "Breads and butter",
                "Very awesome list item has very awesome subtitle. This is bit long",
                0.5,
                R.drawable.food12
            ),
            Meal(
                13,
                "Cupcake factory",
                "Very awesome list item has very awesome subtitle. This is bit long",
                3.2,
                R.drawable.food13
            ),
            Meal(
                14,
                "Breakfast paradise",
                "Very awesome list item has very awesome subtitle. This is bit long",
                2.5,
                R.drawable.food14
            ),
            Meal(
                15,
                "Cake and Bake",
                "Very awesome list item has very awesome subtitle. This is bit long",
                5.5,
                R.drawable.food15
            ),
            Meal(
                16,
                "Brunch and Stakes",
                "Very awesome list item has very awesome subtitle. This is bit long",
                10.0,
                R.drawable.food16
            )
        )
    }

}