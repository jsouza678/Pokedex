package com.souza.search.data.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.souza.pokecatalog.data.pokedex.local.PokemonDao
import com.souza.pokecatalog.domain.model.Pokemon
import com.souza.search.data.mapper.PokedexMapper
import com.souza.search.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val pokemonDao: PokemonDao
) : SearchRepository {

    override fun searchPokesById(poke: Int): LiveData<List<Pokemon>?> {
        return Transformations.map(pokemonDao.getPokesById(poke)) { pokemonEntity ->
            PokedexMapper
                .pokemonEntityAsDomainModel(pokemonEntity)
        }
    }

    override fun searchPokesByName(poke: String): LiveData<List<Pokemon>?> {
        return Transformations.map(pokemonDao.getPokesByName(poke)) {
            PokedexMapper
                .pokemonEntityAsDomainModel(it)
        }
    }
}
