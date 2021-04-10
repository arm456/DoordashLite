package com.example.doordashlite.network.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RestaurantDetailsResponse(
    @Expose val id: Int?,
    @Expose val name: String?,
    @Expose val description: String?,
    @SerializedName("cover_img_url") val coverImageUrl: String?,
    @SerializedName("average_rating") val averageRating: Float?,
    @Expose val address: Address?
)

data class Address(
    @SerializedName("printable_address") val printableAddress: String?
)