package com.souza.pokecatalog.domain.usecase

import com.souza.pokecatalog.domain.repository.PokemonRepository

class FetchPokesFromApi(private val pokemonRepository: PokemonRepository) {
    suspend operator fun invoke(page: Int) = pokemonRepository.refreshPokes(page = page)
}
