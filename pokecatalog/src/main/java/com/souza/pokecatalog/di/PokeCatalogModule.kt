package com.souza.pokecatalog.di

import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.souza.pokecatalog.data.pokedex.PokemonRepositoryImpl
import com.souza.pokecatalog.data.pokedex.local.PokemonDao
import com.souza.pokecatalog.data.pokedex.local.PokemonDatabase
import com.souza.pokecatalog.data.pokedex.remote.PokeCatalogService
import com.souza.pokecatalog.domain.repository.PokemonRepository
import com.souza.pokecatalog.domain.usecase.FetchPokesFromApi
import com.souza.pokecatalog.domain.usecase.GetPokesFromDatabase
import com.souza.pokecatalog.presentation.pokecatalog.PokeCatalogViewModel
import com.souza.pokecatalog.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val pokemonDatabase = "POKEMON_DATABASE"
private const val pokemonDao = "POKEMON_DAO"
private const val pokeCatalogRetrofit = "POKECATALOG_RETROFIT"

@Suppress("RemoveExplicitTypeArguments", "USELESS_CAST")
val pokeCatalogModule = module {

    // ViewModels
    viewModel {
        PokeCatalogViewModel(
            get<GetPokesFromDatabase>(),
            get<FetchPokesFromApi>()
        )
    }

    // Adapter

    // UseCases
    factory {
        FetchPokesFromApi(
            get<PokemonRepository>()
        )
    }

    factory {
        GetPokesFromDatabase(
            get<PokemonRepository>()
        )
    }

    single {
        getRetrofitService(
            get<Retrofit>(named(pokeCatalogRetrofit))
        )
    }

    single(named(pokeCatalogRetrofit)) {
        createRetrofit()
    }

    // Home
    factory {
        PokemonRepositoryImpl(
            pokeCatalogService = get<PokeCatalogService>(),
            pokemonDao = get<PokemonDao>(named(pokemonDao))
        ) as PokemonRepository
    }

    // DB
    single(named(pokemonDatabase)) {
        Room.databaseBuilder(
            androidContext(),
            PokemonDatabase::class.java,
            "pokecatalog.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    // DAO
    single(named(pokemonDao)) {
        get<PokemonDatabase>(named(pokemonDatabase)).pokemonDao
    }
}

private fun getRetrofitService(retrofit: Retrofit): PokeCatalogService = retrofit.create(PokeCatalogService::class.java)

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private fun createRetrofit(): Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(Constants.POKE_API_BASE_URL)
    .build()
