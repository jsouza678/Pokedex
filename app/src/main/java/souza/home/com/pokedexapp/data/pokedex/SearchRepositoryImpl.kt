package souza.home.com.pokedexapp.data.pokedex

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import souza.home.com.pokedexapp.data.pokedex.local.PokemonDatabase
import souza.home.com.pokedexapp.data.pokedex.mappers.PokedexMapper
import souza.home.com.pokedexapp.domain.model.Poke

class SearchRepositoryImpl(context: Context) {

    private val DB_INSTANCE = PokemonDatabase.getDatabase(context)

    fun searchPokesById(poke: Int): LiveData<List<Poke>?> {
        return Transformations.map(DB_INSTANCE.pokemonDao.getPokesById(poke)) {
            PokedexMapper.pokemonAsDomain(it)
        }
    }

    fun searchPokesByName(poke: String): LiveData<List<Poke>?> {
        return Transformations.map(DB_INSTANCE.pokemonDao.getPokesByName(poke)) {
            PokedexMapper.pokemonAsDomain(it)
        }
    }
}
