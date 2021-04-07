package com.example.doordashlite.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.doordashlite.dagger2.DoorDashApplication
import com.example.doordashlite.repository.DoorDashStoreRepository
import com.example.doordashlite.viewmodel.StoresFeedViewModel
import javax.inject.Inject

class StoresFeedFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {

    private lateinit var storesFeedViewModel: StoresFeedViewModel

    @Inject
    lateinit var storeRepository: DoorDashStoreRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ((activity?.application) as DoorDashApplication).appComponent.inject(this)

        storesFeedViewModel =
            ViewModelProvider(this, StoresFeedViewModel.Factory(repository = storeRepository)).get(
                StoresFeedViewModel::class.java
            )

        storesFeedViewModel.getStoreFeedResponse()
    }
}