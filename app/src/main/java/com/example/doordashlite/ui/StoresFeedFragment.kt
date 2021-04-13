package com.example.doordashlite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doordashlite.R
import com.example.doordashlite.dagger2.DoorDashApplication
import com.example.doordashlite.databinding.FragmentStoresFeedBinding
import com.example.doordashlite.repository.DoorDashStoreRepository
import com.example.doordashlite.viewmodel.StoresFeedViewModel
import javax.inject.Inject

class StoresFeedFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {
    private lateinit var storesFeedViewModel: StoresFeedViewModel
    private lateinit var storesAdapter: StoresFeedAdapter

    private var _binding: FragmentStoresFeedBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var storeRepository: DoorDashStoreRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoresFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ((activity?.application) as DoorDashApplication).appComponent.inject(this)
        storesFeedViewModel =
            ViewModelProvider(this, StoresFeedViewModel.Factory(repository = storeRepository)).get(
                StoresFeedViewModel::class.java
            )
        storesFeedViewModel.getStoreFeedResponse(4, 10)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.storesRecyclerView.layoutManager = layoutManager

        val dividerItemDecoration =
            DividerItemDecoration(binding.storesRecyclerView.context, layoutManager.orientation)
        binding.storesRecyclerView.addItemDecoration(dividerItemDecoration)

        storesAdapter = StoresFeedAdapter(storesFeedViewModel)
        binding.storesRecyclerView.adapter = storesAdapter

        storesFeedViewModel.storesLiveData.observe(
            viewLifecycleOwner,
            Observer {
                binding.loadingView.loadingSpinner.visibility = View.GONE
                storesAdapter.setData(it)
            }
        )

        storesFeedViewModel.storeItemClickLiveData.observe(
            viewLifecycleOwner,
            Observer { onClickEvent ->
                onClickEvent.getDataIfReceived()?.let {
                    activity?.supportFragmentManager?.beginTransaction()?.add(
                        R.id.container,
                        RestaurantDetailsFragment.newInstance(
                            it,
                            R.layout.fragment_restaurant_details
                        )
                    )?.addToBackStack(RestaurantDetailsFragment::class.java.canonicalName)?.commit()
                }
            }
        )

        storesFeedViewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            binding.loadingView.loadingSpinner.visibility = View.GONE
            Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_LONG).show()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}