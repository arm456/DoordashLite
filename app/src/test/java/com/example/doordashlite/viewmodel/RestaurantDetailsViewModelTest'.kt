package com.example.doordashlite.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.doordashlite.TestCoroutineRule
import com.example.doordashlite.network.domain.RestaurantDetailsResponse
import com.example.doordashlite.network.domain.RestaurantDetailsResponseResult
import com.example.doordashlite.repository.DoorDashStoreRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class RestaurantDetailsViewModelTest {

    @get:Rule
    internal val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    private lateinit var viewModel: RestaurantDetailsViewModel
    private var repo = mock(DoorDashStoreRepository::class.java)
    private lateinit var mockObserver: Observer<RestaurantDetailsResponse>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = RestaurantDetailsViewModel(repo)
        mockObserver = mock(Observer::class.java) as Observer<RestaurantDetailsResponse>
    }

    @Test
    fun getStoresDetail() = coroutineTestRule.runBlockingTest {
        val storeDetails = RestaurantDetailsResponse(
            0,
            "Indian",
            "Veg Dum Biryani",
            null,
            4.5f,
            null
        )
        `when`(repo.getRestaurantDetails(ArgumentMatchers.anyInt())).thenReturn(
            RestaurantDetailsResponseResult.Success(storeDetails)
        )
        viewModel.restaurantDetailsLiveData.observeForever(mockObserver)
        viewModel.getRestaurantDetails(0)
        Assert.assertEquals(storeDetails, viewModel.restaurantDetailsLiveData.value)
    }
}