package com.example.doordashlite.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.doordashlite.network.domain.Store
import com.example.doordashlite.network.domain.StoreFeedResponseResult
import com.example.doordashlite.repository.DoorDashStoreRepository
import com.example.doordashlite.util.OnClickEvent
import kotlinx.coroutines.launch

class StoresFeedViewModel(private val repository: DoorDashStoreRepository) : ViewModel() {

    private val _storesLiveData = MutableLiveData<MutableList<Store>>()
    val storesLiveData: LiveData<MutableList<Store>> = _storesLiveData

    private val _storeItemClickLiveData = MutableLiveData<OnClickEvent<Int>>()
    val storeItemClickLiveData: LiveData<OnClickEvent<Int>> = _storeItemClickLiveData

    fun getStoreFeedResponse(offset: Int, limit: Int) {
        viewModelScope.launch {
            repository.getDoorDashStoreFeed(37.422740f, -122.139956f, offset, limit)
                .let { storesResult ->
                    when (storesResult) {
                        is StoreFeedResponseResult.Success -> {
                            Log.d(
                                StoresFeedViewModel::class.java.canonicalName,
                                "Store Data fetched from backend"
                            )
                            _storesLiveData.postValue(
                                _storesLiveData.value.apply {
                                    storesResult.storeFeed?.stores?.let {
                                        this?.addAll(
                                            it
                                        )
                                    }
                                } ?: run { storesResult.storeFeed?.stores?.toMutableList() }
                            )
                        }
                        else -> {
                            Log.d(
                                StoresFeedViewModel::class.java.canonicalName,
                                "Data could not be fetched from backend"
                            )
                        }
                    }

                }
        }
    }

    fun onStoreItemClicked(id: Int) {
        _storeItemClickLiveData.postValue(OnClickEvent(id))
    }

    class Factory(private val repository: DoorDashStoreRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return StoresFeedViewModel(repository) as T
        }
    }
}