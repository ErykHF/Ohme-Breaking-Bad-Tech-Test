package com.erykhf.android.ohmebreakingbadtechtest.data.source

import com.erykhf.android.ohmebreakingbadtechtest.data.source.remote.BreakingBadApi
import com.erykhf.android.ohmebreakingbadtechtest.model.BreakingBadCharacterItem
import com.erykhf.android.ohmebreakingbadtechtest.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
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