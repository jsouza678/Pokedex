package souza.home.com.pokedexapp.data.pokedex

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import souza.home.com.pokedexapp.data.pokedex.local.PokemonDatabase
import souza.home.com.pokedexapp.data.pokedex.mapper.PokedexMapper
import souza.home.com.pokedexapp.domain.model.Poke
import souza.home.com.pokedexapp.domain.repository.SearchRepository

class SearchRepositoryImpl(context: Context) : SearchRepository {

    private val INSTANCE = PokemonDatabase.getDatabase(context)

    override fun searchPokesById(poke: Int): LiveData<List<Poke>?> {
        return Transformations.map(INSTANCE.pokemonDao.getPokesById(poke)) {
            PokedexMapper.pokemonAsDomain(it)
        }
    }

    override fun searchPokesByName(poke: String): LiveData<List<Poke>?> {
        return Transformations.map(INSTANCE.pokemonDao.getPokesByName(poke)) {
            PokedexMapper.pokemonAsDomain(it)
        }
    }
}
