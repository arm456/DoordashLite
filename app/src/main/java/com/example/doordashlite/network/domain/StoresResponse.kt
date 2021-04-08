package com.example.doordashlite.network.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StoreFeedResponse(
    @SerializedName("num_results") val numResults: Int?,
    @SerializedName("is_first_time_user") val isFirstTimeUser: Boolean?,
    @SerializedName("sort_order") val sortOrder: String?,
    @SerializedName("next_offset") val nextOffset: Int?,
    @SerializedName("show_list_as_pickup") val showListAsPickup: Boolean?,
    @Expose val stores: List<Store>?
)

data class Store(
    @Expose val id: Int?,
    @Expose val name: String?,
    @Expose val description: String?,
    @SerializedName("cover_img_url") val coverImageUrl: String?,
    @Expose val status: Status?
)

data class Status(
    @SerializedName("unavailable_reason") val unavailableReason: String,
    @SerializedName("pickup_available") val pickupAvailable: Boolean?,
    @SerializedName("asap_available") val asapAvailable: Boolean?,
    @SerializedName("scheduled_available") val scheduledAvailable: Boolean?,
    @SerializedName("asap_minutes_range") val asapMinutesRange: IntArray?
)