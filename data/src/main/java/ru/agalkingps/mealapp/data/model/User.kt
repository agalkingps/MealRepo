package ru.agalkingps.mealapp.data.model


import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user_table", // User Entity represents a table within the database.
    indices = [
        Index("email", unique = true),
        Index(value = ["first_name", "last_name"])])
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int=0,
    @NonNull @ColumnInfo(name = "first_name")
    var firstName: String="",
    @NonNull @ColumnInfo(name = "last_name")
    var lastName: String="",
    @NonNull var email: String="",
    var password: String="",
    @NonNull var balance: Double=0.0,
    @ColumnInfo(name = "photo_url")
    var photoUrl: String?=null
)

