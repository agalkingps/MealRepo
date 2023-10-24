package ru.agalkingps.mealapp.order_flow

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.agalkingps.mealapp.data.model.Meal

const val tag = "MealFlowLog"

class OrderViewModel(context: Context)  : ViewModel()  {

    private val userRepository = ru.agalkingps.mealapp.services.ServiceLocator.getUserRepository(context)
    private val mealRepository = ru.agalkingps.mealapp.services.ServiceLocator.getMealRepository(context)

    private val _mealStateFlow = MutableStateFlow<List<Meal>>(emptyList())
    val mealStateFlow = _mealStateFlow.asStateFlow()

    private var job: Job? = null

    init {
        collectFlowToStateFlow()
    }
    fun collectFlowToStateFlow() {
        stopCollectFlowToStateFlow()
        job = viewModelScope.launch {
            mealRepository.getAllMeals().collect { value ->
                Log.d(tag, "[StateFlow]: Assigning $_mealStateFlow $value to _stateFlow")
                _mealStateFlow.value += value
            }
        }
        val i = 0
    }

    fun stopCollectFlowToStateFlow() = job?.cancel()

}