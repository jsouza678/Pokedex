package souza.home.com.pokedexapp.domain.repository

import androidx.lifecycle.LiveData
import souza.home.com.pokedexapp.domain.model.Pokemon

interface SearchRepository {

    fun searchPokesById(poke: Int): LiveData<List<Pokemon>?>

    fun searchPokesByName(poke: String): LiveData<List<Pokemon>?>
}
