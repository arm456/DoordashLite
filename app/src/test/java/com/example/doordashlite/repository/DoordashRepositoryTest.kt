package com.example.doordashlite.repository

import com.example.doordashlite.network.api.DoorDashAPI
import com.example.doordashlite.network.domain.*
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import retrofit2.Call
import retrofit2.Response

class DoorDashRepositoryTest {
    private lateinit var doorDashStoreRepository: DoorDashStoreRepository
    private var doorDashAPI = Mockito.mock(DoorDashAPI::class.java)

    @Before
    fun setUp() {
        doorDashStoreRepository = DoorDashStoreRepositoryImpl(doorDashAPI)
    }

    @Test
    fun getDoorDashStoreFeedSuccess() = runBlocking {
        val store = Store(
            30,
            "India Garden",
            "Hyderabad Biryani",
            null,
            null
        )
        val responseList = listOf(store)
        Mockito.`when`(
            doorDashAPI.getDoorDashStoreFeed(
                ArgumentMatchers.anyFloat(),
                ArgumentMatchers.anyFloat(),
                ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyInt()
            )
        ).thenReturn(
            Mockito.mock(Call::class.java) as Call<StoreFeedResponse>
        )

        val call = doorDashAPI.getDoorDashStoreFeed(0f, 0f, 1, 1)
        Mockito.`when`(call.execute()).thenReturn(
            Response.success(
                StoreFeedResponse(
                    numResults = 20,
                    isFirstTimeUser = false,
                    sortOrder = "",
                    nextOffset = 0,
                    showListAsPickup = false,
                    stores = responseList
                )
            )
        )

        val expectedStoreFeedResult: StoreFeedResponseResult =
            doorDashStoreRepository.getDoorDashStoreFeed(0f, 0f, 0, 10)
        assertTrue(expectedStoreFeedResult is StoreFeedResponseResult.Success)
        val expectedSuccessResult = expectedStoreFeedResult as StoreFeedResponseResult.Success
        assertEquals(expectedSuccessResult.storeFeed?.stores, responseList)
    }

    @Test
    fun getRestaurantDetailsSuccess() = runBlocking {
        val store = RestaurantDetailsResponse(
            id = 30,
            name = "India Garden",
            description = "Hyderabad Biryani",
            coverImageUrl = null,
            averageRating = 4.5f,
            address = null

        )
        Mockito.`when`(
            doorDashAPI.getRestaurantDetails(ArgumentMatchers.anyInt())
        ).thenReturn(
            Mockito.mock(Call::class.java) as Call<RestaurantDetailsResponse>
        )

        val call = doorDashAPI.getRestaurantDetails(1)
        Mockito.`when`(call.execute()).thenReturn(
            Response.success(store)
        )

        val expectedStoreDetailResult: RestaurantDetailsResponseResult =
            doorDashStoreRepository.getRestaurantDetails(0)
        assertTrue(expectedStoreDetailResult is RestaurantDetailsResponseResult.Success)
        val expectedSuccessResult =
            expectedStoreDetailResult as RestaurantDetailsResponseResult.Success
        assertEquals(expectedSuccessResult.store, store)
    }
}