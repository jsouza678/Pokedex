package com.souza.search.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import com.souza.search.data.pokedex.SearchRepositoryImpl
import com.souza.search.domain.repository.SearchRepository
import com.souza.search.domain.usecase.SearchPokesById
import com.souza.search.domain.usecase.SearchPokesByName
import souza.home.com.pokecatalog.data.pokedex.local.PokemonDao
import souza.home.com.pokedexapp.presentation.search.SearchViewModel

private const val pokemonDao = "POKEMON_DAO"

@Suppress("RemoveExplicitTypeArguments", "USELESS_CAST")
val searchModule = module {

    // ViewModels
    viewModel {
        SearchViewModel(
            get<SearchPokesByName>(),
            get<SearchPokesById>()
        )
    }

    // Adapter
    //TODO put adapter from home

    // UseCases

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

    // Search
    factory {
        SearchRepositoryImpl(
            pokemonDao = get<PokemonDao>(named(pokemonDao))
        ) as SearchRepository
    }

}
