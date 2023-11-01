package ru.agalkingps.mealapp.order_flow

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.agalkingps.mealapp.data.model.Meal

const val tag = "MealFlowLog"

class OrderViewModel(context: Context)  : ViewModel()  {

    private val userRepository = ru.agalkingps.mealapp.services.ServiceLocator.getUserRepository(context)
    private val mealRepository = ru.agalkingps.mealapp.services.ServiceLocator.getMealRepository(context)

    var userId : MutableState<Int> = mutableStateOf(-1)
    var screen : MutableState<String> = mutableStateOf("")

    var mealStateList : MutableState<SnapshotStateList<Meal>> = mutableStateOf(mutableStateListOf<Meal>())

    var mealSelectedCount : MutableState<Int> = mutableStateOf(0)
    private var job: Job? = null

    init {
        collectFlowToStateList()
    }

    private fun collectFlowToStateList() {
        stopCollectFlowToStateList()
        mealStateList.value.clear()
        job = viewModelScope.launch {
            mealRepository.getAllMeals().collect { list ->
                Log.d(tag, "[StateFlow]: Assigning $mealStateList $list to _stateFlow")
                for (meal in list) {
                    mealStateList.value.add(meal)
                }
            }
        }
    }
    private fun stopCollectFlowToStateList() = job?.cancel()

    fun toggleMealSelection(index : Int) {
        var meal: Meal = mealStateList.value[index].copy()
        if (meal.isSelected) {
            meal.isSelected = false
            mealSelectedCount.value--
        } else {
            meal.isSelected = true
            mealSelectedCount.value++
        }
        mealStateList.value[index] = meal
    }

}
