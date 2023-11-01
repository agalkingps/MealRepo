package ru.agalkingps.mealapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Meal (
    val id: Int,
    val title: String,
    val subtitle: String,
    val price: Double,
    val imageId: Int,
    val source: String = "demo source",
    var isSelected: Boolean = false
) {
    override fun toString() : String {
        return "Meal(${id}, ${title}, ${imageId}, ${price}"
    }
}