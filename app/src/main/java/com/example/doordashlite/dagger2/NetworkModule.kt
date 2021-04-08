package com.example.doordashlite.dagger2

import com.example.doordashlite.network.api.DoorDashAPI
import com.example.doordashlite.repository.DoorDashStoreRepository
import com.example.doordashlite.repository.DoorDashStoreRepositoryImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun getDoorDashRepository(service: DoorDashAPI): DoorDashStoreRepository =
        DoorDashStoreRepositoryImpl(service)

    /**
     * Provides the DoorDashAPI service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the DoorDashAPI service implementation.
     */
    @Provides
    internal fun getDoorDashAPI(retrofit: Retrofit): DoorDashAPI {
        return retrofit.create(DoorDashAPI::class.java)
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    internal fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Provides the OkHttpClient object.
     * @return the OkHttpClient object
     */
    @Provides
    internal fun getOkHttClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    companion object {
        private const val BASE_URL = "https://api.doordash.com/"
    }
}