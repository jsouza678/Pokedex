package com.souza.search.di

import com.souza.pokecatalog.data.pokedex.local.PokemonDao
import com.souza.search.data.pokedex.SearchRepositoryImpl
import com.souza.search.domain.repository.SearchRepository
import com.souza.search.domain.usecase.SearchPokesByName
import com.souza.search.presentation.PokeSearchAdapter
import com.souza.search.presentation.PokeSearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val pokemonDao = "POKEMON_DAO"

@Suppress("RemoveExplicitTypeArguments", "USELESS_CAST")
val searchModule = module {

    // ViewModels
    viewModel {
        PokeSearchViewModel(
            get<SearchPokesByName>()
        )
    }

    single {
        PokeSearchAdapter()
    }

    // UseCases
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
