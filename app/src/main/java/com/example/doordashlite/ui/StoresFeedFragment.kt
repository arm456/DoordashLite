package com.example.doordashlite.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doordashlite.R
import com.example.doordashlite.dagger2.DoorDashApplication
import com.example.doordashlite.repository.DoorDashStoreRepository
import com.example.doordashlite.viewmodel.StoresFeedViewModel
import javax.inject.Inject

class StoresFeedFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {

    private lateinit var storesFeedViewModel: StoresFeedViewModel
    private lateinit var storesAdapter: StoresFeedAdapter

    @Inject
    lateinit var storeRepository: DoorDashStoreRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ((activity?.application) as DoorDashApplication).appComponent.inject(this)
        storesFeedViewModel =
            ViewModelProvider(this, StoresFeedViewModel.Factory(repository = storeRepository)).get(
                StoresFeedViewModel::class.java
            )
        storesFeedViewModel.getStoreFeedResponse(4, 10)

        val storesRecyclerView = view.findViewById<RecyclerView>(R.id.stores_recycler_view)
        val layoutManager = LinearLayoutManager(requireContext())
        storesRecyclerView.layoutManager = layoutManager

        val dividerItemDecoration =
            DividerItemDecoration(storesRecyclerView.context, layoutManager.orientation)
        storesRecyclerView.addItemDecoration(dividerItemDecoration)

        storesAdapter = StoresFeedAdapter(storesFeedViewModel)
        storesRecyclerView.adapter = storesAdapter

        storesFeedViewModel.storesLiveData.observe(
            viewLifecycleOwner,
            Observer {
                storesAdapter.setData(it)
            }
        )
    }
}