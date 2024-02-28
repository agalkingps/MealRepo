package ru.agalkingps.mealapp.repo

import androidx.room.Transaction
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.agalkingps.mealapp.data.UserRepository
import ru.agalkingps.mealapp.data.model.Order
import ru.agalkingps.mealapp.data.model.User
import ru.agalkingps.mealapp.repo.dao.UserDao

class LocalUserRepository(private val userDao: UserDao) : UserRepository {

    override suspend fun addUser(user: User) : Long {
        return userDao.addUser(user)
    }

    override suspend fun updateUser(user: User) : Int {
        return userDao.updateUser(user)
    }

    override suspend fun deleteUser(user: User) : Int {
        return userDao.deleteUser(user)
    }

    override suspend fun deleteAllUsers() : Int {
        return userDao.deleteAllUsers()
    }

    override fun getAllUsers() : Flow<List<User>> {
        return userDao.getAllUsers()
    }

    override suspend fun getUserById(id: Int): User?
        = userDao.getUserById(id)

    override suspend fun getUserByEmail(email: String): User?
        = userDao.getUserByEmail(email)

    override suspend fun getUserByIdWithOrders(userId: Int): Map<User, List<Order>>
        = userDao.getUserByIdWithOrders(userId)

    override suspend fun getOrdersByUserId(userId: Int): Flow<List<Order>>
            = userDao.getOrdersByUserId(userId)
    @Transaction
    override suspend fun addOrder(user: User, order: Order) : Long {
        if (order.orderedMeal.isEmpty())
            return 0
        order.userId = user.id;
        order.orderedMeal.forEach {
            user.balance += it.price * it.qty
        }
        if (userDao.updateUser(user) == 0)
            return 0
        return userDao.addOrder(order)
    }
}


