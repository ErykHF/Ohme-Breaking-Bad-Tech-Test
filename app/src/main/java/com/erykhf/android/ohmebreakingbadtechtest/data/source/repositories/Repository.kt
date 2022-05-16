package com.erykhf.android.ohmebreakingbadtechtest.data.source.repositories

import com.erykhf.android.ohmebreakingbadtechtest.data.source.remote.BreakingBadApi
import com.erykhf.android.ohmebreakingbadtechtest.model.BreakingBadCharacterItem
import com.erykhf.android.ohmebreakingbadtechtest.util.Resource
import java.lang.Exception
import javax.inject.Inject

class Repository @Inject constructor(private val api: BreakingBadApi) {

    suspend fun loadBBCharacters(): Resource<List<BreakingBadCharacterItem>> {

        val result = try {
            api.getCharacters()
        } catch (e: Exception) {
            return Resource.Error("Some Kind Of Error Occurred")
        }
        return Resource.Success(result)
    }
}