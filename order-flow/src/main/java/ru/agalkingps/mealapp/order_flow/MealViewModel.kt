package ru.agalkingps.mealapp.order_flow

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.agalkingps.mealapp.data.model.Meal

const val tag = "MealFlowLog"

class MealViewModel()  : ViewModel()  {

    private val userRepository = ru.agalkingps.mealapp.services.ServiceLocator.getUserRepository()
    private val mealRepository = ru.agalkingps.mealapp.services.ServiceLocator.getMealRepository()

    var mealStateList : MutableState<SnapshotStateList<Meal>> = mutableStateOf(mutableStateListOf<Meal>())
    var mealSelectedCount : MutableState<Int> = mutableStateOf(0)

    var orderedMealStateList : MutableState<SnapshotStateList<Meal>> = mutableStateOf(mutableStateListOf<Meal>())
    var orderedMealSelectedCount : MutableState<Int> = mutableStateOf(0)

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

    fun putSelectedMealInShoppingCart() {
        for (idx in mealStateList.value.indices) {
            var meal = mealStateList.value[idx]
            if (meal.isSelected) {
                toggleMealSelection(idx)
                var meal1 = meal.copy()
                meal1.isSelected = false
                var found = false
          loop@ for (idx2 in orderedMealStateList.value.indices) {
                    var meal2 = orderedMealStateList.value[idx2]
                    if (meal2.id == meal1.id) {
                        meal1.count = meal2.count + 1
                        orderedMealStateList.value[idx2] = meal1
                        found = true
                        break@loop
                    }
                }
                if (!found) {
                    meal1.count = 1
                    orderedMealStateList.value.add(meal1)
                }
            }
        }
    }

    fun toggleOrderedMealSelection(index : Int) {
        var meal: Meal = orderedMealStateList.value[index].copy()
        if (meal.isSelected) {
            meal.isSelected = false
            orderedMealSelectedCount.value--
        } else {
            meal.isSelected = true
            orderedMealSelectedCount.value++
        }
        orderedMealStateList.value[index] = meal
    }

    fun removeSelectedOrderedMeal() {
        val iterator = orderedMealStateList.value.iterator()
        for (meal in iterator) {
            if (meal.isSelected) {
                if (meal.count == 1) {
                    iterator.remove()
                }
            }
        }
        for (idx in orderedMealStateList.value.indices) {
            var meal = orderedMealStateList.value[idx]
            if (meal.isSelected && meal.count > 1) {
                meal.isSelected = false
                meal.count--
                orderedMealStateList.value[idx] = meal
            }
        }
        orderedMealSelectedCount.value = 0
    }

    fun calcOrderedMealCoast() : Double {
        var coast = 0.0
        for (meal in orderedMealStateList.value) {
            coast += meal.price * meal.count
        }
        return coast
    }

    fun orderSelectedMeal() {
    }
}
