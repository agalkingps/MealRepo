package ru.agalkingps.mealapp

import android.content.Context
import ru.agalkingps.mealapp.data.MealRepositoryInterface
import ru.agalkingps.mealapp.data.UserRepositoryInterface
import ru.agalkingps.mealapp.repo.database.FakeMealDatabase
import ru.agalkingps.mealapp.repo.database.UserDatabase

object ServiceLocator {
    fun getMealRepository(context : Context) : MealRepositoryInterface {
        return  FakeMealDatabase.getRepository(context)
    }

    fun getUserRepository(context : Context) : UserRepositoryInterface {
        return UserDatabase.getRepository(context)
    }
}