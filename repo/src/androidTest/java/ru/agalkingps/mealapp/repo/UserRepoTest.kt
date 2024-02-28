package ru.agalkingps.mealapp.repo

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
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
    var user1: User = User(1, "a1", "g1", "ag1@mail.ru")
    var user2: User = User(2, "a2", "g2", "ag2@mail.ru")

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
    fun addUserAndGetByEmail() {
        runTest {
            val res = userDao.addUser(user1)
            val user: User? = userDao.getUserByEmail(user1.email)
            assertEquals(user1, user)
        }
    }

    @Test
    @Throws(Exception::class)
    fun deleteUser() {
        runTest {
            addTwoUsers()
            userDao.deleteUser(user1)
            userDao.deleteUser(user2)
            val allItems = userDao.getAllUsers().first()
            assertTrue(allItems.isEmpty())
        }
    }

    @Test
    @Throws(Exception::class)
    fun getAllUsers() = runBlocking {
        addTwoUsers()
        val allItems = userDao.getAllUsers().first()
        assertEquals(allItems[0], user1)
        assertEquals(allItems[1], user2)
    }
    private suspend fun addTwoUsers() {
        userDao.addUser(user1)
        userDao.addUser(user2)
    }


}

