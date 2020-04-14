package souza.home.com.pokedexapp.presentation.search

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import souza.home.com.pokedexapp.R

class SearchViewModelFactory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchDialogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchDialogViewModel(app) as T
        }
        throw IllegalArgumentException(app.applicationContext.getString(R.string.unknown_viewmodel))
    }
}
