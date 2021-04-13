package com.example.doordashlite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.doordashlite.R
import com.example.doordashlite.dagger2.DoorDashApplication
import com.example.doordashlite.databinding.FragmentRestaurantDetailsBinding
import com.example.doordashlite.repository.DoorDashStoreRepository
import com.example.doordashlite.viewmodel.RestaurantDetailsViewModel
import javax.inject.Inject

class RestaurantDetailsFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {
    private lateinit var detailsViewModel: RestaurantDetailsViewModel
    private var _binding: FragmentRestaurantDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var storeRepository: DoorDashStoreRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRestaurantDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ((activity?.application) as DoorDashApplication).appComponent.inject(this)
        detailsViewModel = ViewModelProvider(
            this, RestaurantDetailsViewModel.Factory(repository = storeRepository)
        ).get(RestaurantDetailsViewModel::class.java)

       detailsViewModel.restaurantDetailsLiveData.observe(viewLifecycleOwner, Observer { store ->
            binding.loadingView.loadingSpinner.visibility = View.GONE
            binding.restaurantTitle.text = store.name
            binding.restaurantDesc.text = store.description
            store.coverImageUrl?.let {
                Glide.with(view.context).load(it).into(binding.restaurantDetailImageView)
            }
            store.averageRating?.let {
                binding.restaurantRating.rating = it
            }
            binding.restaurantAddress.text =
                "${context?.resources?.getString(R.string.store_address)} ${store.address?.printableAddress}"
        })

        detailsViewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            binding.loadingView.loadingSpinner.visibility = View.GONE
            Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_LONG).show()
        })
        arguments?.getInt(RESTAURANT_ID)?.let { detailsViewModel.getRestaurantDetails(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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