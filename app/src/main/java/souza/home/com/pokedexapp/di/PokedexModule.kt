package souza.home.com.pokedexapp.di

import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import souza.home.com.pokedexapp.data.pokedex.EvolutionRepositoryImpl
import souza.home.com.pokedexapp.data.pokedex.PokemonRepositoryImpl
import souza.home.com.pokedexapp.data.pokedex.SearchRepositoryImpl
import souza.home.com.pokedexapp.data.pokedex.local.EvolutionChainDao
import souza.home.com.pokedexapp.data.pokedex.local.PokemonDao
import souza.home.com.pokedexapp.data.pokedex.local.PokemonDatabase
import souza.home.com.pokedexapp.data.pokedex.remote.PokedexService
import souza.home.com.pokedexapp.domain.repository.EvolutionRepository
import souza.home.com.pokedexapp.domain.repository.PokemonRepository
import souza.home.com.pokedexapp.domain.repository.SearchRepository
import souza.home.com.pokedexapp.domain.usecase.*
import souza.home.com.pokedexapp.presentation.detailsfragment.details_nested.evolution_chain.EvolutionChainViewModel
import souza.home.com.pokedexapp.presentation.homefragment.HomeViewModel
import souza.home.com.pokedexapp.presentation.search.SearchViewModel
import souza.home.com.pokedexapp.utils.Constants

private const val pokemonDatabase = "POKEMON_DATABASE"

val pokedexModule = module {

    //ViewModels
    viewModel {
        HomeViewModel(
            get<GetPokesFromDatabase>(),
            get<FetchPokesFromApi>()
        )
    }

    viewModel {
        SearchViewModel(
            get<SearchPokesByName>(),
            get<SearchPokesById>()
        )
    }

    viewModel { (chainId: Int) ->
        EvolutionChainViewModel(
            chainId,
            get<FetchEvolutionChainFromApi>(),
            get<GetEvolutionChainFromDatabase>()
        )
    }

    //UseCases
    factory {
        FetchEvolutionChainFromApi(
            get<EvolutionRepository>()
        )
    }

    factory {
        SearchPokesById(
            get<SearchRepository>()
        )
    }

    factory {
        SearchPokesByName(
            get<SearchRepository>()
        )
    }

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

    factory {
        GetEvolutionChainFromDatabase(
            get<EvolutionRepository>()
        )
    }

    //Home
    factory {
        PokemonRepositoryImpl(
            context = get(),
            pokedexService = get<PokedexService>(),
            pokemonDao = get<PokemonDao>()
        ) as PokemonRepository
    }

    //Details
    //EvolutionChain
    factory {
        EvolutionRepositoryImpl(
            context = get(),
            pokedexService = get<PokedexService>(),
            evolutionChainDao = get<EvolutionChainDao>()
        ) as EvolutionRepository
    }

    //Search
    factory {
        SearchRepositoryImpl(
            context = get(),
            pokemonDao = get<PokemonDao>()
        ) as SearchRepository
    }

    //Retrofit
    single {
        getRetrofitService(
            get<Retrofit>()
        )
    }

    single {
        createRetrofit()
    }

    //DB
    single {
        get<PokemonDatabase>(named(pokemonDatabase)).pokemonDao
    }

    single {
        get<PokemonDatabase>(named(pokemonDatabase)).evolutionChainDao
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
