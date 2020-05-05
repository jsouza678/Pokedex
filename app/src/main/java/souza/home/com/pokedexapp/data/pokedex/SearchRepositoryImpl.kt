package souza.home.com.pokedexapp.data.pokedex

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import souza.home.com.pokedexapp.data.pokedex.local.PokemonDao
import souza.home.com.pokedexapp.data.pokedex.mapper.PokedexMapper
import souza.home.com.pokedexapp.domain.model.Poke
import souza.home.com.pokedexapp.domain.repository.SearchRepository

class SearchRepositoryImpl(
    context: Context,
    private val pokemonDao: PokemonDao
) : SearchRepository {

    override fun searchPokesById(poke: Int): LiveData<List<Poke>?> {
        return Transformations.map(pokemonDao.getPokesById(poke)) {
            PokedexMapper.pokemonAsDomain(it)
        }
    }

    override fun searchPokesByName(poke: String): LiveData<List<Poke>?> {
        return Transformations.map(pokemonDao.getPokesByName(poke)) {
            PokedexMapper.pokemonAsDomain(it)
        }
    }
}
