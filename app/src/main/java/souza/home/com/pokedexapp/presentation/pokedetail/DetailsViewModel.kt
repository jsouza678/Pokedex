package souza.home.com.pokedexapp.presentation.pokedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.domain.model.PokeProperty
import souza.home.com.pokedexapp.domain.model.PokeVariety
import souza.home.com.pokedexapp.domain.usecase.GetPropertiesFromApi
import souza.home.com.pokedexapp.domain.usecase.GetPropertiesFromDatabase
import souza.home.com.pokedexapp.domain.usecase.GetVarietiesFromApi
import souza.home.com.pokedexapp.domain.usecase.GetVarietiesFromDatabase

class DetailsViewModel(
    private val pokemon: Int,
    private val getVarietiesFromApi: GetVarietiesFromApi,
    private val getVarietiesFromDatabase: GetVarietiesFromDatabase,
    private val getPropertiesFromApi: GetPropertiesFromApi,
    private val getPropertiesFromDatabase: GetPropertiesFromDatabase
) : ViewModel() {

    private val coroutineScope = Dispatchers.IO
    fun updateVariationsOnViewLiveData(): LiveData<PokeVariety?>? = getVarietiesFromDatabase(pokemon)
    fun updatePropertiesOnViewLiveData(): LiveData<PokeProperty>? = getPropertiesFromDatabase(pokemon)

    init {
        getColor(pokemon)
    }

    private fun getColor(pokemon: Int) {
        viewModelScope.launch(coroutineScope) {
            getVarietiesFromApi(pokemon)
            getPropertiesFromApi(pokemon)
        }
    }
}
