package souza.home.com.pokedexapp.presentation.pokedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.domain.model.PokeProperty
import souza.home.com.pokedexapp.domain.model.PokeVariety
import souza.home.com.pokedexapp.domain.usecase.FetchPropertiesFromApi
import souza.home.com.pokedexapp.domain.usecase.FetchVarietiesFromApi
import souza.home.com.pokedexapp.domain.usecase.GetPropertiesFromDatabase
import souza.home.com.pokedexapp.domain.usecase.GetVarietiesFromDatabase

class DetailsViewModel(
    private val pokemonId: Int,
    private val fetchVarietiesFromApi: FetchVarietiesFromApi,
    private val getVarietiesFromDatabase: GetVarietiesFromDatabase,
    private val fetchPropertiesFromApi: FetchPropertiesFromApi,
    private val getPropertiesFromDatabase: GetPropertiesFromDatabase
) : ViewModel() {

    private val coroutineScope = Dispatchers.IO
    fun updateVariationsOnViewLiveData(): LiveData<PokeVariety?>? = getVarietiesFromDatabase(pokemonId)
    fun updatePropertiesOnViewLiveData(): LiveData<PokeProperty>? = getPropertiesFromDatabase(pokemonId)

    init {
        getColor(pokemonId)
    }

    private fun getColor(pokemon: Int) {
        viewModelScope.launch(coroutineScope) {
            fetchVarietiesFromApi(pokemon)
            fetchPropertiesFromApi(pokemon)
        }
    }
}
