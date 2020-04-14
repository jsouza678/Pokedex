package souza.home.com.pokedexapp.presentation.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.SearchRepositoryImpl
import souza.home.com.pokedexapp.domain.model.Poke

class SearchDialogViewModel(app: Application) : AndroidViewModel(app){

    private val searchRepository = SearchRepositoryImpl(app.applicationContext)

    fun searchForItemsById(poke: Int) : LiveData<List<Poke>?> {
        return searchRepository.searchPokesById(poke)
    }

    fun searchForItemsByName(poke: String) : LiveData<List<Poke>?> {
        return searchRepository.searchPokesByName(poke)
    }
}

class SearchFactory(val app: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchDialogViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return SearchDialogViewModel(app) as T
        }
        throw IllegalArgumentException(app.applicationContext.getString(R.string.unknown_viewmodel))
    }
}