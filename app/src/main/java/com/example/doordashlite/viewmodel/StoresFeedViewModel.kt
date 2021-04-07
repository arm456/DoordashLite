package com.example.doordashlite.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.doordashlite.repository.DoorDashStoreRepository
import kotlinx.coroutines.launch

class StoresFeedViewModel(private val repository: DoorDashStoreRepository) : ViewModel() {

    fun getStoreFeedResponse() {
        viewModelScope.launch {
            repository.getDoorDashStoreFeed(37.422740f, -122.139956f, 10, 10)
        }
        Log.d("DATA ","fetched")
    }

    class Factory(private val repository: DoorDashStoreRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
           return StoresFeedViewModel(repository) as T
        }
    }
}