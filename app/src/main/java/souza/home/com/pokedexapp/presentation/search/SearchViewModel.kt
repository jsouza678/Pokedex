package souza.home.com.pokedexapp.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import souza.home.com.pokedexapp.domain.model.Poke
import souza.home.com.pokedexapp.domain.usecase.SearchPokesById
import souza.home.com.pokedexapp.domain.usecase.SearchPokesByName

class SearchViewModel(
    var searchPokesByName: SearchPokesByName,
    var searchPokesById: SearchPokesById
) : ViewModel() {

    fun searchForItemsById(id: Int): LiveData<List<Poke>?> {
        return searchPokesById(id)
    }
    fun searchForItemsByName(name: String): LiveData<List<Poke>?> {
        return searchPokesByName(name)
    }
}
