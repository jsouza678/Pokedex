package souza.home.com.pokedexapp.data.pokedex

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import souza.home.com.pokedexapp.data.pokedex.local.PokemonDatabase
import souza.home.com.pokedexapp.data.pokedex.mappers.PokedexMapper
import souza.home.com.pokedexapp.domain.model.Poke

class SearchRepositoryImpl(context: Context, poke: String){

    private val DB_INSTANCE = PokemonDatabase.getDatabase(context)

    val pokesById: LiveData<List<Poke>?> = Transformations.map(DB_INSTANCE.pokemonDao.getPokesById(Integer.parseInt(poke))) {
        PokedexMapper.pokemonAsDomain(it)
    }
}