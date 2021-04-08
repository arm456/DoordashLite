package com.example.doordashlite.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.doordashlite.network.domain.Store
import com.example.doordashlite.network.domain.StoreFeedResponseResult
import com.example.doordashlite.repository.DoorDashStoreRepository
import kotlinx.coroutines.launch

class StoresFeedViewModel(private val repository: DoorDashStoreRepository) : ViewModel() {

    private val _storesLiveData = MutableLiveData<MutableList<Store>>()
    val storesLiveData: LiveData<MutableList<Store>> = _storesLiveData

    fun getStoreFeedResponse(offset: Int, limit: Int) {
        viewModelScope.launch {
            repository.getDoorDashStoreFeed(37.422740f, -122.139956f, offset, limit)
                .let { responseResult ->
                    when (responseResult) {
                        is StoreFeedResponseResult.Success -> {
                            Log.d(StoresFeedViewModel::class.java.canonicalName, "Data fetched from backend")
                            _storesLiveData.postValue(
                                _storesLiveData.value.apply {
                                    responseResult.storeFeed?.stores?.let {
                                        this?.addAll(
                                            it
                                        )
                                    }
                                } ?: run { responseResult.storeFeed?.stores?.toMutableList() }
                            )
                        }
                        else -> {
                            Log.d(StoresFeedViewModel::class.java.canonicalName, "Data could not be fetched from backend")
                        }
                    }

                }
        }

    }

    class Factory(private val repository: DoorDashStoreRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return StoresFeedViewModel(repository) as T
        }
    }
}