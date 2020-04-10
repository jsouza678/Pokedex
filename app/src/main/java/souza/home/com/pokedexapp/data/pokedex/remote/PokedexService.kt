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
import souza.home.com.pokedexapp.data.pokedex.remote.model.PokeRootProperty
import souza.home.com.pokedexapp.data.pokedex.remote.model.ability.PokeAbilityRoot
import souza.home.com.pokedexapp.data.pokedex.remote.model.evolution_chain.PokeEvolutionChain
import souza.home.com.pokedexapp.data.pokedex.remote.model.stats.PokemonProperty
import souza.home.com.pokedexapp.data.pokedex.remote.model.types.PokeTypeRoot
import souza.home.com.pokedexapp.data.pokedex.remote.model.varieties.PokeVarietiesResponse

private const val BASE_URL = "https://pokeapi.co/api/v2/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface PokeService{
    @GET("pokemon/?")
    fun getPokes(@Query("offset") page: Int?):
            Deferred<PokeRootProperty>

    @GET("pokemon/{poke}")
    fun getPokeStats(@Path("poke") poke: String?):
            Deferred<PokemonProperty>

    @GET("evolution-chain/{id}")
    fun getEvolutionChain(@Path("id") id : String?):
            Deferred<PokeEvolutionChain>

    @GET("pokemon-species/{id}")
    fun getVariations(@Path("id") id : String?):
            Deferred<PokeVarietiesResponse>

    @GET("type/{id}")
    fun getTypeData(@Path("id") id: String?):
            Deferred<PokeTypeRoot>

    @GET("ability/{id}")
    fun getAbilityData(@Path("id") id: String?):
            Deferred<PokeAbilityRoot>
}

object PokeApi{
    val retrofitService: PokeService by lazy { retrofit.create(
        PokeService::class.java) }
}