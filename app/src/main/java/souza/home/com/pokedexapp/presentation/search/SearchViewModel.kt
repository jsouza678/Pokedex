package souza.home.com.pokedexapp.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import souza.home.com.pokedexapp.domain.model.Pokemon
import souza.home.com.pokedexapp.domain.usecase.SearchPokesById
import souza.home.com.pokedexapp.domain.usecase.SearchPokesByName

class SearchViewModel(
    var searchPokesByName: SearchPokesByName,
    var searchPokesById: SearchPokesById
) : ViewModel() {

    fun searchPokesById(id: Int): LiveData<List<Pokemon>?> {
        return searchPokesById(id)
    }
    fun searchPokesByName(name: String): LiveData<List<Pokemon>?> {
        return searchPokesByName(name)
    }
}
