package com.erykhf.android.ohmebreakingbadtechtest.data.source

import com.erykhf.android.ohmebreakingbadtechtest.data.source.remote.BreakingBadApi
import com.erykhf.android.ohmebreakingbadtechtest.model.BreakingBadCharacterItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val api: BreakingBadApi) {

    suspend fun loadBBCharacters() = withContext(Dispatchers.IO) {

        val response = api.getCharacters()

        val result = try {
            response
        } catch (cause: Throwable) {
            throw BreakingError("Error", cause)
        }

        if (result.isSuccessful) {
            result.body()
        } else {
            throw BreakingError(result.message().toString(), null)
        }


    }

}

class BreakingError(message: String, cause: Throwable?) : Throwable(message, cause)
