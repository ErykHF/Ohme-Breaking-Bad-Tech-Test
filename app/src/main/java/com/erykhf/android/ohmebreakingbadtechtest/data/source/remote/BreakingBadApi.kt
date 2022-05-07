package com.erykhf.android.ohmebreakingbadtechtest.data.source.remote

import com.erykhf.android.ohmebreakingbadtechtest.model.BreakingBadCharacterItem
import retrofit2.Response
import retrofit2.http.GET

interface BreakingBadApi {

    @GET("characters")
    suspend fun getCharacters(): Response<List<BreakingBadCharacterItem>>
}