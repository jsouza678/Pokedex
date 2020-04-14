package souza.home.com.pokedexapp.presentation.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import souza.home.com.pokedexapp.data.pokedex.SearchRepositoryImpl
import souza.home.com.pokedexapp.domain.model.Poke

class SearchDialogViewModel(app: Application) : AndroidViewModel(app) {

    private val searchRepository = SearchRepositoryImpl(app.applicationContext)

    fun searchForItemsById(poke: Int): LiveData<List<Poke>?> {
        return searchRepository.searchPokesById(poke)
    }
    fun searchForItemsByName(poke: String): LiveData<List<Poke>?> {
        return searchRepository.searchPokesByName(poke)
    }
}
