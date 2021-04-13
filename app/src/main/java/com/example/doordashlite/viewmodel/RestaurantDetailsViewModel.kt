package com.example.doordashlite.viewmodel

import androidx.lifecycle.*
import com.example.doordashlite.network.domain.NetworkException
import com.example.doordashlite.network.domain.RestaurantDetailsResponse
import com.example.doordashlite.network.domain.RestaurantDetailsResponseResult
import com.example.doordashlite.repository.DoorDashStoreRepository
import kotlinx.coroutines.launch

class RestaurantDetailsViewModel(private val repository: DoorDashStoreRepository) : ViewModel() {

    private val _restaurantDetailsLiveData = MutableLiveData<RestaurantDetailsResponse>()
    val restaurantDetailsLiveData: LiveData<RestaurantDetailsResponse> = _restaurantDetailsLiveData

    private val _errorLiveData = MutableLiveData<NetworkException>()
    val errorLiveData: LiveData<NetworkException> = _errorLiveData

    fun getRestaurantDetails(id: Int?) {
        viewModelScope.launch {
            id?.let {
                repository.getRestaurantDetails(it).let { restaurantDetailsResult ->
                    when (restaurantDetailsResult) {
                        is RestaurantDetailsResponseResult.Success -> {
                            _restaurantDetailsLiveData.postValue(restaurantDetailsResult.store)
                        }
                        is RestaurantDetailsResponseResult.Failure -> {
                            _errorLiveData.postValue(restaurantDetailsResult.exception)
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