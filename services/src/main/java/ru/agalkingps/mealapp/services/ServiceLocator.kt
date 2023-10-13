package ru.agalkingps.mealapp.services

import android.content.Context
import ru.agalkingps.mealapp.data.UserRepositoryInterface
import ru.agalkingps.mealapp.data.MealRepositoryInterface
import ru.agalkingps.mealapp.repo.database.FakeMealDatabase
import ru.agalkingps.mealapp.repo.database.UserDatabase

object ServiceLocator {
    fun getUserRepository(context : Context) : UserRepositoryInterface {
        return UserDatabase.getRepository(context)
    }
    fun getMealRepository(context : Context) : MealRepositoryInterface {
        return  FakeMealDatabase.getRepository(context)
    }

}