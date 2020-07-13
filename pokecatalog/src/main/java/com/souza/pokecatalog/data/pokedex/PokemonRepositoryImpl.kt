package com.souza.pokecatalog.data.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.souza.pokecatalog.data.pokedex.local.PokemonDao
import com.souza.pokecatalog.data.pokedex.mapper.PokedexMapper
import com.souza.pokecatalog.data.pokedex.remote.PokeCatalogService
import com.souza.pokecatalog.domain.model.Pokemon
import com.souza.pokecatalog.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonRepositoryImpl(
    private val pokeCatalogService: PokeCatalogService,
    private val pokemonDao: PokemonDao
) : PokemonRepository {

    override fun getPokes(): LiveData<List<Pokemon>?> {
        return Transformations.map(pokemonDao.getPokes()) { pokemonEntity ->
            PokedexMapper
                .pokemonEntityAsDomainModel(pokemonEntity = pokemonEntity)
        }
    }

    override suspend fun refreshPokes(page: Int) {
        withContext(Dispatchers.IO) {
            try {
                val pokeList = pokeCatalogService.fetchPokesAsync(page).await()
                PokedexMapper
                    .pokemonResponseAsDatabaseModel(pokeRootProperty = pokeList)?.let {
                    pokemonDao.insertAll(*it)
                }
            } catch (e: Exception) { }
        }
    }
}
