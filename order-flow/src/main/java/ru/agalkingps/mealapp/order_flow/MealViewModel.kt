package ru.agalkingps.mealapp.order_flow

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.agalkingps.mealapp.data.MealRepositoryInterface
import ru.agalkingps.mealapp.data.UserRepositoryInterface
import ru.agalkingps.mealapp.data.model.Meal
import ru.agalkingps.mealapp.data.model.Order
import ru.agalkingps.mealapp.data.model.OrderedMeal
import ru.agalkingps.mealapp.data.model.User
import java.util.Date
import javax.inject.Inject

const val tag = "MealFlowLog"

private const val ITEMS_PER_PAGE = 4

@HiltViewModel
class MealViewModel @Inject constructor()  : ViewModel() {

    @Inject
    lateinit var userRepository: UserRepositoryInterface

    @Inject
    lateinit var mealRepository: MealRepositoryInterface

    var mealSelectedCount: MutableState<Int> = mutableIntStateOf(0)
    @SuppressLint("MutableCollectionMutableState")
    var mealSelectedIds: MutableState<MutableList<Int>> = mutableStateOf(mutableListOf<Int>())

    @SuppressLint("MutableCollectionMutableState")
    var orderedMealStateList: MutableState<SnapshotStateList<Meal>> =
        mutableStateOf(mutableStateListOf())
    var orderedMealSelectedCount: MutableState<Int> = mutableIntStateOf(0)
    @SuppressLint("MutableCollectionMutableState")
    var orderedMealSelectedIds: MutableState<MutableList<Int>> = mutableStateOf(mutableListOf<Int>())

    @SuppressLint("MutableCollectionMutableState")
    var orderStateList: MutableState<SnapshotStateList<Order>> =
        mutableStateOf(mutableStateListOf())
    var totalState: MutableState<Double> = mutableDoubleStateOf(0.0)

    var userState: MutableState<User?> = mutableStateOf(null)

    private var job: Job? = null
    private var job2: Job? = null

     fun toggleMealSelection(id: Int) {
        if (mealSelectedIds.value.contains(id)) {
            mealSelectedIds.value.remove(id)
            mealSelectedCount.value--
        } else {
            mealSelectedIds.value.add(id)
            mealSelectedCount.value++
        }
    }

    fun putSelectedMealInShoppingCart() {
        for (idx in mealSelectedIds.value) {
            val meal1 = mealRepository.getMealById(idx)
            if (meal1 != null) {
                var idx2 = orderedMealStateList.value.indexOfFirst{
                    it.id == meal1.id
                }
                if (idx2 >= 0) {
                    val meal2 = orderedMealStateList.value[idx2]
                    meal1.count = meal2.count + 1
                    orderedMealStateList.value[idx2] = meal1
                }
                else {
                    meal1.count = 1
                    orderedMealStateList.value.add(meal1)
                }
            }
        }
        mealSelectedIds.value.clear()
    }

    fun toggleOrderedMealSelection(id: Int) {
        if (orderedMealSelectedIds.value.contains(id)) {
            orderedMealSelectedIds.value.remove(id)
            orderedMealSelectedCount.value--
        } else {
            orderedMealSelectedIds.value.add(id)
            orderedMealSelectedCount.value++
        }
    }

    fun removeSelectedOrderedMeal() {
        val iterator = orderedMealStateList.value.iterator()
        for (meal in iterator) {
            if (orderedMealSelectedIds.value.contains(meal.id) && meal.count == 1) {
                iterator.remove()
                orderedMealSelectedIds.value.remove(meal.id)
            }
        }
        for (idx in orderedMealStateList.value.indices) {
            val meal = orderedMealStateList.value[idx]
            if (orderedMealSelectedIds.value.contains(meal.id) && meal.count > 1) {
                meal.count--
                orderedMealSelectedIds.value.remove(meal.id)
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
            val user: User = userRepository.getUserById(userId) ?: return@launch
            val mealList: MutableList<OrderedMeal> = mutableListOf()
            var iterator = orderedMealStateList.value.iterator()
            var total = 0.0
            for (meal in iterator) {
                mealList.add(OrderedMeal(meal.id, meal.price, meal.count))
                total += meal.price * meal.count
            }

            val order = Order(userId, Date(), total, mealList)
            userRepository.addOrder(user, order)
            onGotoPay()

            iterator = orderedMealStateList.value.iterator()
            for (meal in iterator) {
                orderedMealSelectedIds.value.remove(meal.id)
                iterator.remove()
            }
        }
        orderedMealSelectedCount.value = 0
    }

    fun fetchUserById(userId: Int) {
        viewModelScope.launch {
            userState.value = userRepository.getUserById(userId)
        }
    }

    fun collectOrderFlowToStateList(userId: Int) {
        orderStateList.value.clear()
        totalState.value = 0.0
        job = viewModelScope.launch {
            userRepository.getOrdersByUserId(userId).collect { list: List<Order> ->
                list.map { order ->
                        orderStateList.value.add(order)
                        totalState.value += order.total
                }
            }
        }
    }

    /**
     * Stream of immutable states representative of the UI.
     */
    val mealPagingDataFlow: Flow<PagingData<Meal>> = Pager(
        config = PagingConfig(
            pageSize = ITEMS_PER_PAGE,
            initialLoadSize = ITEMS_PER_PAGE * 2,
            maxSize = ITEMS_PER_PAGE*4,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { mealRepository.getMealPagingSource() }
    )
        .flow
        // cachedIn allows paging to remain active in the viewModel scope, so even if the UI
        // showing the paged data goes through lifecycle changes, pagination remains cached and
        // the UI does not have to start paging from the beginning when it resumes.
        .cachedIn(viewModelScope)

}