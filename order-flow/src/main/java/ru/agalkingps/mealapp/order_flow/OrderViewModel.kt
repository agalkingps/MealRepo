package ru.agalkingps.mealapp.order_flow

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import ru.agalkingps.mealapp.data.model.Meal

class OrderViewModel(context: Context)  : ViewModel()  {

    private val userRepository = ru.agalkingps.mealapp.services.ServiceLocator.getUserRepository(context)
    private val mealRepository = ru.agalkingps.mealapp.services.ServiceLocator.getMealRepository(context)

    suspend fun getAllMeals(): Flow<List<Meal>> = mealRepository.getAllMeals()
}