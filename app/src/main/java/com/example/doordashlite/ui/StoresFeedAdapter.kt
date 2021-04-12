package com.example.doordashlite.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.doordashlite.R
import com.example.doordashlite.network.domain.Store
import com.example.doordashlite.viewmodel.StoresFeedViewModel


class StoresFeedAdapter(
    private val storeFeedViewModel: StoresFeedViewModel,
    private var storesList: List<Store> = emptyList()
) : RecyclerView.Adapter<StoresFeedAdapter.StoresViewHolder>() {

    fun setData(storesList: List<Store>) {
        this.storesList = storesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoresViewHolder =
        StoresViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_stores, parent, false)
        )

    override fun getItemCount(): Int = if (storesList.isNullOrEmpty()) 0 else storesList.size

    override fun onBindViewHolder(holder: StoresViewHolder, position: Int) {
        val store = storesList[position]
        store.coverImageUrl?.let {
            holder.storeImage?.let { image ->
                Glide.with(holder.itemView.context).load(store.coverImageUrl).into(
                    image
                )
            }
        }
        holder.storeName?.text = store.name
        holder.storeDescription?.text = store.description
        if (store.status?.asapAvailable == true)
            holder.storeStatus?.text =
                "${store.status.asapMinutesRange?.get(0)} ${holder.itemView.context.resources.getString(
                    R.string.minutes
                )}"
        else
            holder.storeStatus?.text = "${store.status?.unavailableReason}"

        holder.itemView.setOnClickListener {
            store.id?.let { it -> storeFeedViewModel.onStoreItemClicked(it) }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItems: Int? = recyclerView.layoutManager?.itemCount
                val scrolledItems: Int? =
                    (recyclerView.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()
                if (totalItems == null || scrolledItems == null) return
                if (scrolledItems <= totalItems) {
                    storeFeedViewModel.getStoreFeedResponse(storesList.size, 10)
                }
            }
        })
    }

    class StoresViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val storeImage: ImageView? = view.findViewById(R.id.store_image)
        val storeName: TextView? = view.findViewById(R.id.store_name)
        val storeDescription: TextView? = view.findViewById(R.id.store_description)
        val storeStatus: TextView? = view.findViewById(R.id.store_status)
    }
}