package com.souza.search.di

import com.souza.pokecatalog.data.pokedex.local.PokemonDao
import com.souza.search.data.pokedex.SearchRepositoryImpl
import com.souza.search.domain.repository.SearchRepository
import com.souza.search.domain.usecase.SearchPokesById
import com.souza.search.domain.usecase.SearchPokesByName
import com.souza.search.presentation.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

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
