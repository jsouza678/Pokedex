package souza.home.com.pokedexapp.data.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import souza.home.com.pokedexapp.data.pokedex.local.PokemonDao
import souza.home.com.pokedexapp.data.pokedex.mapper.PokedexMapper
import souza.home.com.pokedexapp.domain.model.Pokemon
import souza.home.com.pokedexapp.domain.repository.SearchRepository

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
