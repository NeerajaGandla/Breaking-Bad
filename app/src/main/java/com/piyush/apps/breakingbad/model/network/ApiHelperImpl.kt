package com.piyush.apps.breakingbad.model.network

import com.piyush.apps.breakingbad.model.Character
import retrofit2.Response
import javax.inject.Inject

/**
 * Created By Piyush Pandey on 08-07-2020
 * Apps@Piyush
 */
class ApiHelperImpl @Inject constructor(private val bbApi : BreakingBadApi) : ApiHelper {

    override suspend fun getCharacters(): Response<List<Character>> {
        return bbApi.listCharacters()
    }
}