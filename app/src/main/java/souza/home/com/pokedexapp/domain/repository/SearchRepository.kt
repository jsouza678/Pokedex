package souza.home.com.pokedexapp.domain.repository

import androidx.lifecycle.LiveData
import souza.home.com.pokedexapp.domain.model.Poke

interface SearchRepository {

    fun searchPokesById(poke: Int): LiveData<List<Poke>?>

    fun searchPokesByName(poke: String): LiveData<List<Poke>?>
}
