package com.erykhf.android.ohmebreakingbadtechtest.di

import com.erykhf.android.ohmebreakingbadtechtest.data.source.Repository
import com.erykhf.android.ohmebreakingbadtechtest.data.source.remote.BreakingBadApi
import com.erykhf.android.ohmebreakingbadtechtest.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Singleton
    @Provides
    fun getAllCharacters(): BreakingBadApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BreakingBadApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(api: BreakingBadApi) = Repository(api)


}