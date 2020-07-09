package com.piyush.apps.breakingbad.model.repository

import com.piyush.apps.breakingbad.model.Character
import com.piyush.apps.breakingbad.model.network.ApiHelper
import retrofit2.Response
import javax.inject.Inject

/**
 * Created By Piyush Pandey on 08-07-2020
 * Apps@Piyush
 */
class AppRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getCharacters() : Response<List<Character>> {
        return apiHelper.getCharacters()
    }

}