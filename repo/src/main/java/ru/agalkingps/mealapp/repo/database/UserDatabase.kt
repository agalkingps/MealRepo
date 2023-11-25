package ru.agalkingps.mealapp.repo.database

import ru.agalkingps.mealapp.data.model.User
import ru.agalkingps.mealapp.data.model.Order
import ru.agalkingps.mealapp.repo.dao.UserDao
import ru.agalkingps.mealapp.data.converters.DateTimeTypeConverters

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.agalkingps.mealapp.data.UserRepositoryInterface
import ru.agalkingps.mealapp.data.converters.OrderedMealListTypeConverter
import ru.agalkingps.mealapp.repo.UserRepository


// UserDatabase represents database and contains the database holder and server the main access point for the underlying connection to your app's persisted, relational data.

@Database(
    entities = [User::class, Order::class],
    version = 1,                // <- Database version
    exportSchema = true
)
@TypeConverters(DateTimeTypeConverters::class,
    OrderedMealListTypeConverter::class)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "meal_order_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        fun getRepository(appContext: Context): UserRepositoryInterface {
            val userDao = UserDatabase.getDatabase(appContext).userDao()
            return UserRepository(userDao)
        }

        fun close() {
        }
    }
}