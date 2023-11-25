package ru.agalkingps.mealapp.order_flow

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.text.font.FontLoadingStrategy.Companion.Async
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.agalkingps.mealapp.data.MealRepositoryInterface
import ru.agalkingps.mealapp.data.UserRepositoryInterface
import ru.agalkingps.mealapp.data.model.Meal
import ru.agalkingps.mealapp.data.model.Order
import ru.agalkingps.mealapp.data.model.OrderedMeal
import ru.agalkingps.mealapp.data.model.User
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

const val tag = "MealFlowLog"

@HiltViewModel
class MealViewModel @Inject constructor()  : ViewModel() {

    @Inject
    lateinit var userRepository: UserRepositoryInterface
    @Inject
    lateinit var mealRepository: MealRepositoryInterface

    var mealStateList: MutableState<SnapshotStateList<Meal>> =
        mutableStateOf(mutableStateListOf<Meal>())
    var mealSelectedCount: MutableState<Int> = mutableStateOf(0)

    var orderedMealStateList: MutableState<SnapshotStateList<Meal>> =
        mutableStateOf(mutableStateListOf<Meal>())
    var orderedMealSelectedCount: MutableState<Int> = mutableStateOf(0)

    var orderStateList: MutableState<SnapshotStateList<Order>> =
        mutableStateOf(mutableStateListOf<Order>())
    var totalState: MutableState<Double> = mutableStateOf(0.0)

    var userState: MutableState<User?> = mutableStateOf(null)

    private var job: Job? = null
    private var job2: Job? = null

    companion object {
        var collectingMeal = false
        var collectingOrders = false
    }

    fun collectMealFlowToStateList() {
        if (collectingMeal) {
            return
        }
        stopCollectMealFlowToStateList()
        collectingMeal = true
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

    private fun stopCollectMealFlowToStateList() = job?.cancel()

    fun toggleMealSelection(index: Int) {
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

    fun toggleOrderedMealSelection(index: Int) {
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

    fun calcOrderedMealCoast(): Double {
        var coast = 0.0
        for (meal in orderedMealStateList.value) {
            coast += meal.price * meal.count
        }
        return coast
    }

    fun orderSelectedMeal(userId: Int, onGotoPay: () -> Unit) {
        job2?.cancel()
        job2 = viewModelScope.launch {
            var user: User? = userRepository.getUserById(userId) ?: return@launch
            var mealList: MutableList<OrderedMeal> = mutableListOf()
            val iterator = orderedMealStateList.value.iterator()
            var total: Double = 0.0
            for (meal in iterator) {
                mealList.add(OrderedMeal(meal.id, meal.price, meal.count))
                total += meal.price * meal.count
                iterator.remove()
            }

            var order = Order(userId, Date(), total, mealList)
            userRepository.addOrder(user!!, order)
            onGotoPay()
        }
    }

    fun fetchUserById(userId: Int) {
        val job = viewModelScope.launch {
            userState.value = userRepository.getUserById(userId)
        }
    }

    fun collectOrderFlowToStateList(userId: Int) {
        if (collectingOrders) {
            return
        }
        collectingOrders = true
        orderStateList.value.clear()
        totalState.value = 0.0
        job = viewModelScope.launch {
            mealRepository.getAllMeals().collect { list ->
                userRepository.getOrdersByUserId(userId).collect { list ->
                    for (order in list) {
                        orderStateList.value.add(order)
                        totalState.value += order.total
                    }
                }
            }
        }
    }

}