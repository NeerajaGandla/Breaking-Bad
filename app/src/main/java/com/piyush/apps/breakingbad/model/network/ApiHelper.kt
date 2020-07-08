package com.piyush.apps.breakingbad.model.network

import com.piyush.apps.breakingbad.model.Character
import retrofit2.Response

/**
 * Created By Piyush Pandey on 08-07-2020
 * Apps@Piyush
 */
interface ApiHelper {
    suspend fun getCharacters() : Response<List<Character>>
}