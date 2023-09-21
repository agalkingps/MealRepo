package ru.agalkingps.mealapp.repo.database

import android.content.Context
import ru.agalkingps.mealapp.data.MealRepositoryInterface
import ru.agalkingps.mealapp.repo.FakeMealRepository

class FakeMealDatabase {
    companion object {
        @Volatile
        private var INSTANCE: MealRepositoryInterface? = null

        fun getRepository(appContext : Context): MealRepositoryInterface{
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = FakeMealRepository()
                instance.fillMMealList()
                INSTANCE = instance
                return instance
            }
        }
    }
}