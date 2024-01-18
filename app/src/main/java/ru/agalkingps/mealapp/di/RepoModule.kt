package ru.agalkingps.mealapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.agalkingps.mealapp.data.MealRepository
import ru.agalkingps.mealapp.data.UserRepository
import ru.agalkingps.mealapp.repo.database.FakeMealDatabase
import ru.agalkingps.mealapp.repo.database.UserDatabase

import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepoModule {

    @Provides
    @Singleton
    fun provideUserRepository(@ApplicationContext appContext: Context) : UserRepository {
        return UserDatabase.getRepository(appContext)
    }

    @Provides
    @Singleton
    fun provideMealRepository(@ApplicationContext appContext: Context) : MealRepository {
        return  FakeMealDatabase.getRepository(appContext)
    }

}
