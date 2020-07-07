package com.piyush.apps.breakingbad.model.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created By Piyush Pandey on 04-07-2020
 * Apps@Piyush
 */
class NetworkService {

    companion object {
        fun getNetworkService(): BreakingBadService {
            return Retrofit.Builder()
                .baseUrl("https://www.breakingbadapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BreakingBadService::class.java)
        }
    }
}