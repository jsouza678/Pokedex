package souza.home.com.pokedexapp.di

import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.PokemonRepositoryImpl
import souza.home.com.pokedexapp.data.pokedex.local.PokemonDao
import souza.home.com.pokedexapp.data.pokedex.local.PokemonDatabase
import souza.home.com.pokedexapp.data.pokedex.remote.PokedexService
import souza.home.com.pokedexapp.domain.repository.PokemonRepository
import souza.home.com.pokedexapp.domain.usecase.FetchPokesFromApi
import souza.home.com.pokedexapp.domain.usecase.GetPokesFromDatabase
import souza.home.com.pokedexapp.presentation.homefragment.HomeViewModel
import souza.home.com.pokedexapp.utils.Constants

private const val pokemonDatabase = "POKEMON_DATABASE"

val pokedexModule = module {

    viewModel {
       HomeViewModel(
           get<GetPokesFromDatabase>(),
           get<FetchPokesFromApi>()
       )
    }

    factory {
        GetPokesFromDatabase(
            get<PokemonRepository>()
        )
    }

    factory {
        FetchPokesFromApi(
            get<PokemonRepository>()
        )
    }

    single {
        get<PokemonDatabase>(named(pokemonDatabase)).pokemonDao
    }

    factory {
        PokemonRepositoryImpl(
            context = get(),
            pokedexService = get<PokedexService>(),
            pokemonDao = get<PokemonDao>()
        ) as PokemonRepository
    }

    single {
        getRetrofitService(
            get<Retrofit>()
        )
    }

    single {
        createRetrofit()
    }

    single(named(pokemonDatabase)) {
        Room.databaseBuilder(
            androidContext(),
            PokemonDatabase::class.java,
            "teste"
        ).build()
    }
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private fun createRetrofit() : Retrofit =  Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(Constants.POKE_API_BASE_URL)
    .build()

private fun getRetrofitService(retrofit: Retrofit): PokedexService =
    retrofit.create(PokedexService::class.java)
