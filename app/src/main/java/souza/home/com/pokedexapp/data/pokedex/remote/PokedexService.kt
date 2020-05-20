package souza.home.com.pokedexapp.data.pokedex.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import souza.home.com.pokedexapp.data.pokedex.remote.response.AbilitiesRootResponse
import souza.home.com.pokedexapp.data.pokedex.remote.response.EvolutionChainResponse
import souza.home.com.pokedexapp.data.pokedex.remote.response.PokeRootResponse
import souza.home.com.pokedexapp.data.pokedex.remote.response.PropertyRootResponse
import souza.home.com.pokedexapp.data.pokedex.remote.response.TypesRootResponse
import souza.home.com.pokedexapp.data.pokedex.remote.response.VarietiesRootResponse
import souza.home.com.pokedexapp.utils.Constants

interface PokedexService {
    @GET("pokemon/?")
    fun fetchPokesAsync(@Query("offset") page: Int?):
            Deferred<PokeRootResponse> // Cached

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
            Deferred<AbilitiesRootResponse> // Non-Chached
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(Constants.POKE_API_BASE_URL)
    .build()

object PokeApi {
    val retrofitService: PokedexService by lazy {
        retrofit.create(
            PokedexService::class.java
        )
    }
}
