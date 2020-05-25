package com.souza.pokecatalog.data.pokedex.remote

import com.souza.pokecatalog.data.pokedex.remote.model.PokeRootResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeCatalogService {

    @GET("pokemon/?")
    fun fetchPokesAsync(@Query("offset") page: Int?):
            Deferred<PokeRootResponse> // Cached
}
