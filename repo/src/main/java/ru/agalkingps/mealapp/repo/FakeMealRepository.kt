package ru.agalkingps.mealapp.repo

import androidx.paging.PagingSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.agalkingps.mealapp.data.model.Meal
import ru.agalkingps.mealapp.data.MealRepository


class FakeMealRepository  : MealRepository {

    private var provider = FakeMealProvider()

    override fun getAllMeals(): Flow<List<Meal>> = flow {
        val list : MutableList<Meal> = mutableListOf()
        var idx: Int = provider.minIndex
        var meal = provider.getMealById(idx)
        while (meal != null && idx <= provider.maxIndex){
            list.add(meal)
            delay(100L)
            emit(list)
            list.clear()
            meal = provider.getMealById(++idx)
        }
    }

    override fun getMealById(mealId: Int):  Meal? {
        return provider.getMealById(mealId)
    }

    /**
     * [PagingSource] for [Meal]
     */
    override fun getMealPagingSource() = FakeMealPagingSource()
}