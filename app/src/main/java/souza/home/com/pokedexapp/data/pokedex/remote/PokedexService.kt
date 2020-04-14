package souza.home.com.pokedexapp.data.pokedex.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import souza.home.com.pokedexapp.di.PokedexModule
import souza.home.com.pokedexapp.utils.Constants.Companion.POKE_API_BASE_URL

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(POKE_API_BASE_URL)
    .build()

object PokeApi {
    val retrofitService: PokedexModule by lazy {
        retrofit.create(
            PokedexModule::class.java
        )
    }
}
