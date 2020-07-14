package com.souza.pokecatalog.domain.usecase

import androidx.lifecycle.LiveData
import com.souza.pokecatalog.domain.model.Pokemon
import com.souza.pokecatalog.domain.repository.PokemonRepository

class GetPokesFromDatabase(private val pokemonRepository: PokemonRepository) {

    operator fun invoke(): LiveData<List<Pokemon>?> = pokemonRepository.getPokes()
}
