package ru.agalkingps.mealapp.services

import android.content.Context
import ru.agalkingps.mealapp.data.UserRepositoryInterface
import ru.agalkingps.mealapp.data.MealRepositoryInterface
import ru.agalkingps.mealapp.repo.database.FakeMealDatabase
import ru.agalkingps.mealapp.repo.database.UserDatabase

object ServiceLocator {
    fun getUserRepository() : UserRepositoryInterface {
        return UserDatabase.getRepository(appContext)
    }
    fun getMealRepository() : MealRepositoryInterface {
        return  FakeMealDatabase.getRepository(appContext)
    }
    lateinit var appContext: Context

}