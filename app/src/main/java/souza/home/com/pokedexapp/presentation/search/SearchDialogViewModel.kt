package souza.home.com.pokedexapp.presentation.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.SearchRepositoryImpl
import souza.home.com.pokedexapp.domain.model.Poke

class SearchByIdDialogViewModel(app: Application, poke: String) : AndroidViewModel(app){

    fun updatePokeslListOnViewLiveData(): LiveData<List<Poke>?> = searchRepository.pokesById
    private val searchRepository = SearchRepositoryImpl(app.applicationContext, poke)
}

class SearchFactory(val app: Application, val poke: String): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchByIdDialogViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return SearchByIdDialogViewModel(app, poke) as T
        }
        throw IllegalArgumentException(app.applicationContext.getString(R.string.unknown_viewmodel))
    }
}