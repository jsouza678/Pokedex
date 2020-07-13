package com.souza.pokedetail.data.pokedex.remote

import com.souza.pokedetail.data.pokedex.remote.response.AbilityRootResponse
import com.souza.pokedetail.data.pokedex.remote.response.EvolutionChainResponse
import com.souza.pokedetail.data.pokedex.remote.response.PropertyRootResponse
import com.souza.pokedetail.data.pokedex.remote.response.TypesRootResponse
import com.souza.pokedetail.data.pokedex.remote.response.VarietiesRootResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeDetailService {

    @GET("pokemon/{poke}")
    fun fetchPokeStatsAsync(@Path("poke") poke: Int?): Deferred<PropertyRootResponse>

    @GET("evolution-chain/{id}")
    fun fetchEvolutionChainAsync(@Path("id") id: Int?): Deferred<EvolutionChainResponse>

    @GET("pokemon-species/{id}")
    fun fetchVariationsAsync(@Path("id") id: Int?): Deferred<VarietiesRootResponse>

    @GET("type/{id}")
    fun fetchTypeDataAsync(@Path("id") id: Int?): Deferred<TypesRootResponse>

    @GET("ability/{id}")
    fun fetchAbilityDataAsync(@Path("id") id: Int): Deferred<AbilityRootResponse>
}
