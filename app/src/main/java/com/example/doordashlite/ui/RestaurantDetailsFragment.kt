package com.example.doordashlite.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.doordashlite.dagger2.DoorDashApplication
import com.example.doordashlite.repository.DoorDashStoreRepository
import com.example.doordashlite.viewmodel.RestaurantDetailsViewModel
import javax.inject.Inject

class RestaurantDetailsFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {

    private lateinit var detailsViewModel: RestaurantDetailsViewModel

    @Inject
    lateinit var storeRepository: DoorDashStoreRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ((activity?.application) as DoorDashApplication).appComponent.inject(this)
        detailsViewModel = ViewModelProvider(
            this, RestaurantDetailsViewModel.Factory(repository = storeRepository)
        ).get(RestaurantDetailsViewModel::class.java)
    }

    companion object {
        private const val RESTAURANT_ID = "RESTAURANT_ID"
        @JvmStatic
        fun newInstance(id: Int, contentLayoutId: Int): RestaurantDetailsFragment {
            return RestaurantDetailsFragment(contentLayoutId).apply {
                arguments = Bundle().apply {
                    putInt(RESTAURANT_ID, id)
                }
            }
        }
    }

}