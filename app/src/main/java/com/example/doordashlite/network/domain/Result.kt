package com.example.doordashlite.network.domain

/**
 * Sealed class for Success, failure & Loading results for DoorDash store feed
 */
sealed class StoreFeedResponseResult {
    data class Success(val storeFeed: StoreFeedResponse?) : StoreFeedResponseResult()
    data class Failure(val exception: NetworkException) : StoreFeedResponseResult()
}

/**
 * Sealed class for Success, failure & Loading results for DoorDash restaurant details
 */
sealed class RestaurantDetailsResponseResult {
    data class Success(val store: RestaurantDetailsResponse?) : RestaurantDetailsResponseResult()
    data class Failure(val exception: NetworkException) : RestaurantDetailsResponseResult()
}

data class NetworkException(
    val errorMessage: String?
) : Exception()