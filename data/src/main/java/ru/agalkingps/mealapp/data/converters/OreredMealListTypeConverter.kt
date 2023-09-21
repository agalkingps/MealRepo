package ru.agalkingps.mealapp.data.converters

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.agalkingps.mealapp.data.model.OrderedMeal

class OrderedMealListTypeConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun gettingListFromString(str: String?): List<OrderedMeal>? {
            val list = str?.let { Json.decodeFromString<List<OrderedMeal>>(it) };
            return list
        }

        @TypeConverter
        @JvmStatic
        fun writingStringFromList(list: List<OrderedMeal>?): String? {
            return Json.encodeToString<List<OrderedMeal>?>(list)
        }
    }
}