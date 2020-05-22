package com.souza.shared_components.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.souza.shared_components.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val pokedexRetrofit = "POKEDEX_RETROFIT"

@Suppress("RemoveExplicitTypeArguments", "USELESS_CAST")
val sharedModule = module {

    single(named(pokedexRetrofit)) {
        createRetrofit()
    }
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private fun createRetrofit(): Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(Constants.POKE_API_BASE_URL)
    .build()
