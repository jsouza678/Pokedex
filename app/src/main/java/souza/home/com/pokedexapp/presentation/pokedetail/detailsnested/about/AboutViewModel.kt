package souza.home.com.pokedexapp.presentation.pokedetail.detailsnested.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.domain.model.PokeVariety
import souza.home.com.pokedexapp.domain.usecase.GetVarietiesFromApi
import souza.home.com.pokedexapp.domain.usecase.GetVarietiesFromDatabase

class AboutViewModel(
    private val pokemon: Int,
    private val getVarietiesFromApi: GetVarietiesFromApi,
    private val getVarietiesFromDatabase: GetVarietiesFromDatabase
) : ViewModel() {

    private val coroutineScope = Dispatchers.IO
    fun updateVariationsOnViewLiveData(): LiveData<PokeVariety?>? = getVarietiesFromDatabase(pokemon)

    init {
        getVarieties(pokemon)
    }

    fun getVarieties(pokemon: Int) {
        viewModelScope.launch(coroutineScope) {
            getVarietiesFromApi(pokemon)
        }
    }
}
