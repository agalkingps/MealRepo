package ru.agalkingps.mealapp.repo

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import ru.agalkingps.mealapp.data.model.User
import ru.agalkingps.mealapp.repo.dao.UserDao
import ru.agalkingps.mealapp.repo.database.UserDatabase
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class UserRepoTest {
    private lateinit var userDao: UserDao
    private lateinit var db: UserDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java).build()
        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
         var user: User = User(1, "a", "g", "ag@mail.ru")

        runTest {
            try {
                val res = userDao.addUser(user)
                val user2: User? = userDao.getUserByEmail(user.email)
                assertThat(user2, equalTo(user))
            }
            catch(exp : Exception) {
                val e = exp
            }
        }
    }
}

