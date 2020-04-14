package souza.home.com.pokedexapp.di

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import souza.home.com.pokedexapp.data.pokedex.remote.response.AllAbilitiesResponse
import souza.home.com.pokedexapp.data.pokedex.remote.response.AllTypesResponse
import souza.home.com.pokedexapp.data.pokedex.remote.response.EvolutionChainResponse
import souza.home.com.pokedexapp.data.pokedex.remote.response.PokeRootResponse
import souza.home.com.pokedexapp.data.pokedex.remote.response.PropertyResponse
import souza.home.com.pokedexapp.data.pokedex.remote.response.VarietiesResponse

interface PokedexModule {
    @GET("pokemon/?")
    fun getPokes(@Query("offset") page: Int?):
            Deferred<PokeRootResponse> // Cached

    @GET("pokemon/{poke}")
    fun getPokeStats(@Path("poke") poke: Int?):
            Deferred<PropertyResponse> // Cached

    @GET("evolution-chain/{id}")
    fun getEvolutionChain(@Path("id") id: Int?):
            Deferred<EvolutionChainResponse> // Cached

    @GET("pokemon-species/{id}")
    fun getVariations(@Path("id") id: Int?):
            Deferred<VarietiesResponse> // Cached

    @GET("type/{id}")
    fun getTypeData(@Path("id") id: Int?):
            Deferred<AllTypesResponse>

    @GET("ability/{id}")
    fun getAbilityData(@Path("id") id: Int):
            Deferred<AllAbilitiesResponse>
}
