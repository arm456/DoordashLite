package com.example.doordashlite.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.doordashlite.R
import com.example.doordashlite.network.domain.Store


class StoresFeedAdapter(
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
        holder.storeName?.text = store.name
        holder.storeDescription?.text = store.description
        if (store.status?.asapAvailable == true)
            holder.storeStatus?.text =
                "${store.status.asapMinutesRange?.get(0)} ${holder.itemView.context.resources.getString(R.string.minutes)}"
        else
            holder.storeStatus?.text = "${store.status?.unavailableReason}"
    }

    class StoresViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val storeImage: ImageView? = view.findViewById(R.id.store_image)
        val storeName: TextView? = view.findViewById(R.id.store_name)
        val storeDescription: TextView? = view.findViewById(R.id.store_description)
        val storeStatus: TextView? = view.findViewById(R.id.store_status)
    }
}