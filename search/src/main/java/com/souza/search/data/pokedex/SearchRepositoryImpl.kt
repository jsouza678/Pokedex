package com.souza.search.data.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import souza.home.com.pokecatalog.data.pokedex.local.PokemonDao
import souza.home.com.pokecatalog.domain.model.Pokemon
import com.souza.search.data.mapper.PokedexMapper
import com.souza.search.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val pokemonDao: PokemonDao
) : SearchRepository {

    override fun searchPokesById(poke: Int): LiveData<List<Pokemon>?> {
        return Transformations.map(pokemonDao.getPokesById(poke)) {
            PokedexMapper.pokemonEntityAsDomainModel(it)
        }
    }

    override fun searchPokesByName(poke: String): LiveData<List<Pokemon>?> {
        return Transformations.map(pokemonDao.getPokesByName(poke)) {
            PokedexMapper.pokemonEntityAsDomainModel(it)
        }
    }
}
