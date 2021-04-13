package com.example.doordashlite.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.doordashlite.TestCoroutineRule
import com.example.doordashlite.network.domain.Store
import com.example.doordashlite.network.domain.StoreFeedResponse
import com.example.doordashlite.network.domain.StoreFeedResponseResult
import com.example.doordashlite.repository.DoorDashStoreRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers.anyFloat
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

class StoresFeedViewModelTest {

    @get:Rule
    internal val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    private lateinit var viewModel: StoresFeedViewModel
    private var repo = mock(DoorDashStoreRepository::class.java)
    private lateinit var mockObserver: Observer<MutableList<Store>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = StoresFeedViewModel(repo)
        mockObserver = mock(Observer::class.java) as Observer<MutableList<Store>>
    }

    @Test
    fun getDoorDashStoreFeed() = coroutineTestRule.runBlockingTest {
        val store = Store(
            30,
            "India Garden",
            "Hyderabad Biryani, Pepper corn fry",
            null,
            null
        )
        val stores = mutableListOf(store)
        `when`(repo.getDoorDashStoreFeed( anyFloat(), anyFloat(),anyInt(), anyInt()))
            .thenReturn(StoreFeedResponseResult.Success(StoreFeedResponse(40, true, "s", 5, false, stores)))
        viewModel.storesLiveData.observeForever(mockObserver)
        viewModel.getStoreFeedResponse(0,10)
        assertEquals(stores, viewModel.storesLiveData.value)
    }
}