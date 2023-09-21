package ru.agalkingps.mealapp.data.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import kotlinx.serialization.Serializable
import java.util.Date

@Entity(tableName = "order_table", // Order Entity represents a table within the database
        primaryKeys = ["user_id","meal_id","date"],
        foreignKeys = [ForeignKey(
            entity = User::class,
            childColumns = ["user_id"],
            parentColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        )])
data class Order (
    @NonNull @ColumnInfo(name = "user_id")
    var userId: Int=0,
    @NonNull @ColumnInfo(name = "meal_id")
    var mealId: Int=0,
    @NonNull @ColumnInfo(name = "date")
    var timestamp: Date,
    @NonNull @ColumnInfo(name = "ordered_meal")
    var orderedMeal : List<OrderedMeal>
)

@Serializable
data class OrderedMeal (
    var mealId: Int,
    val price : Double,
    val qty : Long
)