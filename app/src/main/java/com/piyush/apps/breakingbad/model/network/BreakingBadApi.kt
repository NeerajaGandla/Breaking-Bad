package com.piyush.apps.breakingbad.model.network

import com.piyush.apps.breakingbad.model.Character
import retrofit2.Response
import retrofit2.http.GET
/**
 * Created By Piyush Pandey on 04-07-2020
 * Apps@Piyush
 */
interface BreakingBadApi {
    @GET("/api/characters")
    suspend fun listCharacters() : Response<List<Character>>
}