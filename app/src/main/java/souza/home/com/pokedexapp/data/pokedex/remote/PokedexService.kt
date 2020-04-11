package souza.home.com.pokedexapp.data.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import souza.home.com.pokedexapp.data.pokedex.remote.model.pokemon.PokeRootProperty
import souza.home.com.pokedexapp.data.pokedex.remote.model.ability.AllAbilities
import souza.home.com.pokedexapp.data.pokedex.remote.model.evolution_chain.EvolutionChain
import souza.home.com.pokedexapp.data.pokedex.remote.model.response.PropertyResponse
import souza.home.com.pokedexapp.data.pokedex.remote.model.type.AllTypes
import souza.home.com.pokedexapp.data.pokedex.remote.model.response.VarietiesResponse
import souza.home.com.pokedexapp.utils.Constants.Companion.POKE_API_BASE_URL

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(POKE_API_BASE_URL)
    .build()

interface PokeService{
    @GET("pokemon/?")
    fun getPokes(@Query("offset") page: Int?):
            Deferred<PokeRootProperty>   // Cached

    @GET("pokemon/{poke}")
    fun getPokeStats(@Path("poke") poke: Int?):  // Needs to be cached
            Deferred<PropertyResponse>

    @GET("evolution-chain/{id}")
    fun getEvolutionChain(@Path("id") id : Int?):
            Deferred<EvolutionChain>

    @GET("pokemon-species/{id}")
    fun getVariations(@Path("id") id : Int?):
            Deferred<VarietiesResponse>  // Cached

    @GET("type/{id}")
    fun getTypeData(@Path("id") id: Int?):
            Deferred<AllTypes>

    @GET("ability/{id}")
    fun getAbilityData(@Path("id") id: Int):
            Deferred<AllAbilities>
}

object PokeApi{
    val retrofitService: PokeService by lazy { retrofit.create(
        PokeService::class.java) }
}