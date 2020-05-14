package souza.home.com.pokedexapp.presentation.pokedetail.detailsnested.stats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.data.pokedex.remote.response.PropertyResponse
import souza.home.com.pokedexapp.domain.model.PokeProperty
import souza.home.com.pokedexapp.domain.usecase.GetPropertiesFromApi
import souza.home.com.pokedexapp.domain.usecase.GetPropertiesFromDatabase

class StatsViewModel(
    private val pokemon: Int,
    private val getPropertiesFromApi: GetPropertiesFromApi,
    private val getPropertiesFromDatabase: GetPropertiesFromDatabase
) : ViewModel() {

    private var _stats = MutableLiveData<PropertyResponse>()

    val stats: LiveData<PropertyResponse>
        get() = _stats

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun updatePropertiesOnViewLiveData(): LiveData<PokeProperty>? = getPropertiesFromDatabase(pokemon)

    init {
        getStats(pokemon)
    }

    private fun getStats(pokemon: Int) {
        coroutineScope.launch {
            getPropertiesFromApi(pokemon)
        }
    }
}
