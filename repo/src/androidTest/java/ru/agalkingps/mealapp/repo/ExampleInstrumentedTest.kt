package ru.agalkingps.mealapp.repo

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import ru.agalkingps.mealapp.data.MealRepository
import ru.agalkingps.mealapp.data.model.Meal
import ru.agalkingps.mealapp.repo.database.FakeMealDatabase

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("ru.agalkingps.mealapp.repo.test", appContext.packageName)

        val repo: MealRepository = FakeMealDatabase.getRepository(appContext)

        runTest {
            val mealList = repo.getAllMeals().collect {
                Log.d("Meal", it.toString())
            }
            val meal: Meal? = repo.getMealById(1)
            if (meal != null) {
                println(meal.toString())
            }
        }
        Thread.sleep(10_000)
    }
}

