package com.piyush.apps.breakingbad.model.network

import com.piyush.apps.breakingbad.model.Character
import retrofit2.Call
import retrofit2.http.GET
/**
 * Created By Piyush Pandey on 04-07-2020
 * Apps@Piyush
 */
interface BreakingBadService {
    @GET("/api/characters")
    fun listCharacters() : Call<List<Character>>
}