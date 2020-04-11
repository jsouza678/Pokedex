package souza.home.com.pokedexapp.presentation.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetailsViewModelFactory(private val pokeId: Int, private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsPokedexViewModel::class.java)) {
            return DetailsPokedexViewModel(pokeId, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}