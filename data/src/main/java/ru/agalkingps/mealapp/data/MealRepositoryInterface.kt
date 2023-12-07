package ru.agalkingps.mealapp.data

import kotlinx.coroutines.flow.Flow
import ru.agalkingps.mealapp.data.model.Meal

interface MealRepositoryInterface {
    fun getAllMeals(): Flow<List<Meal>>
    fun getMealById(mealId: Int): Meal?
}