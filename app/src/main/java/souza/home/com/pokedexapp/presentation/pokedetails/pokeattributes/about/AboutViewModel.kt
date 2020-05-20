package souza.home.com.pokedexapp.presentation.pokedetails.pokeattributes.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.domain.model.PokeVariety
import souza.home.com.pokedexapp.domain.usecase.FetchVarietiesFromApi
import souza.home.com.pokedexapp.domain.usecase.GetVarietiesFromDatabase

class AboutViewModel(
    private val pokemonId: Int,
    private val fetchVarietiesFromApi: FetchVarietiesFromApi,
    private val getVarietiesFromDatabase: GetVarietiesFromDatabase
) : ViewModel() {

    private val coroutineScope = Dispatchers.IO
    fun updateVariationsOnViewLiveData(): LiveData<PokeVariety?>? = getVarietiesFromDatabase(pokemonId)

    init {
        getVarieties(pokemonId)
    }

    fun getVarieties(pokemon: Int) {
        viewModelScope.launch(coroutineScope) {
            fetchVarietiesFromApi(pokemon)
        }
    }
}
