package ru.agalkingps.mealapp.repo

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.agalkingps.mealapp.data.model.Meal
import ru.agalkingps.mealapp.repo.FakeMealProvider
import ru.agalkingps.mealapp.data.MealRepositoryInterface

class FakeMealRepository  : MealRepositoryInterface {

    private var provider = FakeMealProvider()

    override fun getAllMeals(): Flow<List<Meal>> = flow {
        val list : MutableList<Meal> = mutableListOf()
        var idx: Int = 1
        var meal = provider.getMealById(idx)
        while (meal != null){
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

}