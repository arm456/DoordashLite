package com.example.doordashlite.ui

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.doordashlite.R
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

        val restaurantTitle = view.findViewById<TextView>(R.id.restaurant_title)
        val restaurantDescription = view.findViewById<TextView>(R.id.restaurant_desc)
        val restaurantImage = view.findViewById<ImageView>(R.id.restaurant_detail_image_view)
        val restaurantRatingBar = view.findViewById<RatingBar>(R.id.restaurant_rating)
        val restaurantAddress = view.findViewById<TextView>(R.id.restaurant_address)
        val loading = view.findViewById<FrameLayout>(R.id.loading_view)

        detailsViewModel.restaurantDetailsLiveData.observe(viewLifecycleOwner, Observer { store ->
            loading.visibility = View.GONE
            restaurantTitle.text = store.name
            restaurantDescription.text = store.description
            store.coverImageUrl?.let {
                Glide.with(view.context).load(it).into(restaurantImage)
            }
            store.averageRating?.let {
                restaurantRatingBar.rating = it
            }
            restaurantAddress.text = "${context?.resources?.getString(R.string.store_address)} ${store.address?.printableAddress}"
        })

        arguments?.getInt(RESTAURANT_ID)?.let { detailsViewModel.getRestaurantDetails(it) }
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