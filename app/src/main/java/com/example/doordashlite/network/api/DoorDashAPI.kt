package com.example.doordashlite.network.api

import com.example.doordashlite.network.domain.RestaurantDetailsResponse
import com.example.doordashlite.network.domain.StoreFeedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DoorDashAPI {

    @GET("v1/store_feed")
    fun getDoorDashStoreFeed(
        @Query("lat") latitude: Float,
        @Query("lng") longitude: Float,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Call<StoreFeedResponse>

    @GET("v2/restaurant/{id}")
    fun getRestaurantDetails(
        @Path("id") id: Int
    ): Call<RestaurantDetailsResponse>
}