package ru.agalkingps.mealapp.repo

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.delay
import ru.agalkingps.mealapp.data.model.Meal
import kotlin.math.min
import kotlin.math.max

private const val LOAD_DELAY_MILLIS = 3_000L

class MealPagingSource : PagingSource<Int, Meal>() {

    private var provider = FakeMealProvider()

    override suspend fun load(params: LoadParams<Int>): LoadResult.Page<Int, Meal> {
        // If params.key is null, it is the first load, so we start loading with STARTING_KEY
        val startKey = params.key ?: provider.minIndex
        val endKey = min(startKey + params.loadSize - 1, provider.maxIndex)
        // We fetch as many articles as hinted to by params.loadSize
        val range = startKey.rangeTo(endKey)
        Log.d("MealPagingSource", "$range")

        // Simulate a delay for loads after the initial load
        if (startKey != provider.minIndex) delay(LOAD_DELAY_MILLIS)

        return LoadResult.Page(
            data = range.map { number -> provider.getMealById(number)?:
                Meal(number,
                    "Wrong meal index $number",
                    "",
                    0.0,
                    0) },
            prevKey = when (startKey) {
                provider.minIndex -> null
                else -> when (val prevKey =
                    ensureValidPrevKey(key = range.first - params.loadSize)) {
                    // We're at the start, there's nothing more to load
                    provider.minIndex -> null
                    else -> prevKey
                }
            },
            nextKey = when (range.last) {
                provider.maxIndex -> null
                else -> range.last + 1
            }
        )
    }

    // The refresh key is used for the initial load of the next PagingSource, after invalidation
    override fun getRefreshKey(state: PagingState<Int, Meal>): Int? {
        // In our case we grab the item closest to the anchor position
        // then return its id - (state.config.pageSize / 2) as a buffer
        val anchorPosition = state.anchorPosition ?: return null
        val meal = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidPrevKey(key = meal.id - (state.config.pageSize / 2))
    }

    // Makes sure the paging key is never less than [STARTING_KEY]
    private fun ensureValidPrevKey(key: Int) = max(provider.minIndex, key)
    // Makes sure the paging key is never less than [STARTING_KEY]
    private fun ensureValidNextKey(key: Int) = min(provider.maxIndex, key)
}
