package ru.agalkingps.mealapp.data.model

import kotlinx.serialization.Serializable

@Serializable
class Meal (
    val id: Int,
    val title: String,
    val subtitle: String,
    val price: Double,
    val imageId: Int,
    val source: String = "demo source"
) {
    override fun toString() : String {
        return "Meal(${id}, ${title}, ${imageId}, ${price}"
    }
}