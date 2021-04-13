package com.example.doordashlite.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.doordashlite.network.domain.RestaurantDetailsResponse
import com.example.doordashlite.network.domain.RestaurantDetailsResponseResult
import com.example.doordashlite.repository.DoorDashStoreRepository
import kotlinx.coroutines.launch

class RestaurantDetailsViewModel(private val repository: DoorDashStoreRepository) : ViewModel() {

    private val _restaurantDetailsLiveData = MutableLiveData<RestaurantDetailsResponse>()
    val restaurantDetailsLiveData: LiveData<RestaurantDetailsResponse> = _restaurantDetailsLiveData

    fun getRestaurantDetails(id: Int?) {
        viewModelScope.launch {
            id?.let {
                repository.getRestaurantDetails(it).let { restaurantDetailsResult ->
                    when (restaurantDetailsResult) {
                        is RestaurantDetailsResponseResult.Success -> {
                            _restaurantDetailsLiveData.postValue(restaurantDetailsResult.store)
                        }
                        else -> {
                            Log.d(
                                RestaurantDetailsViewModel::class.java.canonicalName,
                                "Detail Data not fetched from backend"
                            )
                        }
                    }
                }
            }
        }
    }

    class Factory(private val repository: DoorDashStoreRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RestaurantDetailsViewModel(repository) as T
        }
    }
}