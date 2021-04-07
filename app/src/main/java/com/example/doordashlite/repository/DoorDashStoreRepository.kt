package com.example.doordashlite.repository

import com.example.doordashlite.network.api.DoorDashAPI
import com.example.doordashlite.network.domain.NetworkException
import com.example.doordashlite.network.domain.RestaurantDetailsResponseResult
import com.example.doordashlite.network.domain.StoreFeedResponseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

interface DoorDashStoreRepository {
    suspend fun getDoorDashStoreFeed(
        latitude: Float,
        longitude: Float,
        offset: Int,
        limit: Int
    ): StoreFeedResponseResult

    suspend fun getRestaurantDetails(
        id: Int
    ): RestaurantDetailsResponseResult
}

class DoorDashStoreRepositoryImpl(private val doorDashAPI: DoorDashAPI) : DoorDashStoreRepository {

    override suspend fun getDoorDashStoreFeed(
        latitude: Float,
        longitude: Float,
        offset: Int,
        limit: Int
    ): StoreFeedResponseResult {
        return try {
//            StoreFeedResponseResult.Loading
            withContext(Dispatchers.IO) {
                val storeFeedResponse =
                    doorDashAPI.getDoorDashStoreFeed(latitude, longitude, offset, limit).execute()
                if (storeFeedResponse.isSuccessful && storeFeedResponse.body() != null) {
                    StoreFeedResponseResult.Success(storeFeedResponse.body())
                } else {
                    StoreFeedResponseResult.Failure(
                        NetworkException(storeFeedResponse.errorBody()?.string())
                    )
                }
            }
        } catch (e: Exception) {
            StoreFeedResponseResult.Failure(exception = NetworkException(e.message))
        }
    }

    override suspend fun getRestaurantDetails(id: Int): RestaurantDetailsResponseResult {
        return try {
            withContext(Dispatchers.IO) {
                val restaurantDetailsResponse =
                    doorDashAPI.getRestaurantDetails(id).execute()
                if (restaurantDetailsResponse.isSuccessful && restaurantDetailsResponse.body() != null) {
                    RestaurantDetailsResponseResult.Success(restaurantDetailsResponse.body())
                } else {
                    RestaurantDetailsResponseResult.Failure(
                        NetworkException(restaurantDetailsResponse.errorBody()?.string())
                    )
                }
            }
        } catch (e: Exception) {
            RestaurantDetailsResponseResult.Failure(exception = NetworkException(e.message))
        }
    }

}