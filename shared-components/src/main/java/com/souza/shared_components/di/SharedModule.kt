package com.souza.shared_components.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.souza.shared_components.utils.Constants
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val SHARED_RETROFIT = "SHARED_RETROFIT"

@Suppress("RemoveExplicitTypeArguments", "USELESS_CAST")
val sharedModule = module {

    single(named(SHARED_RETROFIT)) {
        createRetrofit()
    }
}

private fun createRetrofit(): Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(Constants.POKE_API_BASE_URL)
    .build()
