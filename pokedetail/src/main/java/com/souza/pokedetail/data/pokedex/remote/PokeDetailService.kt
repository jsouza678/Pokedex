package com.souza.pokedetail.data.pokedex.remote

import com.souza.pokedetail.data.pokedex.remote.response.*
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeDetailService {

    @GET("pokemon/{poke}")
    fun fetchPokeStatsAsync(@Path("poke") poke: Int?):
            Deferred<PropertyRootResponse> // Cached

    @GET("evolution-chain/{id}")
    fun fetchEvolutionChainAsync(@Path("id") id: Int?):
            Deferred<EvolutionChainResponse> // Cached

    @GET("pokemon-species/{id}")
    fun fetchVariationsAsync(@Path("id") id: Int?):
            Deferred<VarietiesRootResponse> // Cached

    @GET("type/{id}")
    fun fetchTypeDataAsync(@Path("id") id: Int?):
            Deferred<TypesRootResponse> // Non-Cached

    @GET("ability/{id}")
    fun fetchAbilityDataAsync(@Path("id") id: Int):
            Deferred<AbilityRootResponse> // Cached
}
