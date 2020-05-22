package souza.home.com.pokedexapp.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.souza.search.domain.usecase.SearchPokesById
import com.souza.search.domain.usecase.SearchPokesByName

class SearchViewModel(
    var searchPokesByName: SearchPokesByName,
    var searchPokesById: SearchPokesById
) : ViewModel() {

    fun searchPokesById(id: Int): LiveData<List<souza.home.com.pokecatalog.domain.model.Pokemon>?> {
        return searchPokesById(id)
    }
    fun searchPokesByName(name: String): LiveData<List<souza.home.com.pokecatalog.domain.model.Pokemon>?> {
        return searchPokesByName(name)
    }
}
