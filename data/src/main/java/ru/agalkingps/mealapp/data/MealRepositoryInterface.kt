package ru.agalkingps.mealapp.data

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import ru.agalkingps.mealapp.data.model.Meal

interface MealRepositoryInterface {
    fun getAllMeals(): Flow<List<Meal>>
    fun getMealById(mealId: Int): Meal?
    fun getMealPagingSource(): PagingSource<Int, Meal>
}