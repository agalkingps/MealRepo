package ru.agalkingps.mealapp.repo.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.agalkingps.mealapp.data.model.Order
import ru.agalkingps.mealapp.data.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User) : Long

    @Update
    suspend fun updateUser(user: User) : Int

    @Delete
    suspend fun deleteUser(user: User) : Int

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers() : Int

    @Query("SELECT * FROM user_table ORDER BY first_name ASC, last_name ASC")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM user_table WHERE id = :id")
    suspend fun getUserById(id: Int): User?

    @Query("SELECT * FROM user_table WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM user_table" +
            " JOIN order_table ON user_table.id = order_table.user_id" +
            " WHERE user_table.id = :userId")
    fun getUserByIdWithOrders(userId: Int): Map<User, List<Order>>

    @Insert
    suspend fun addOrder(order: Order) : Long

}
