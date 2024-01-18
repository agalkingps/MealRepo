package ru.agalkingps.mealapp.repo.database

import android.content.Context
import ru.agalkingps.mealapp.data.MealRepository
import ru.agalkingps.mealapp.repo.FakeMealRepository

class FakeMealDatabase {
    companion object {
        @Volatile
        private var INSTANCE: MealRepository? = null

        fun getRepository(appContext : Context): MealRepository{
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = FakeMealRepository()
                INSTANCE = instance
                return instance
            }
        }
    }
}