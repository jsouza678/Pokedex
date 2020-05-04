package souza.home.com.pokedexapp.presentation.detailsfragment.details_nested.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.domain.model.PokeVariety
import souza.home.com.pokedexapp.domain.usecase.GetVarietiesFromApi
import souza.home.com.pokedexapp.domain.usecase.GetVarietiesFromDatabase

class AboutViewModel(private val pokemon: Int,
                     private val getVarietiesFromApi: GetVarietiesFromApi,
                     private val getVarietiesFromDatabase: GetVarietiesFromDatabase
) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    fun updateVariationsOnViewLiveData(): LiveData<PokeVariety?>? = getVarietiesFromDatabase(pokemon)

    init {
        //if (CheckNetworkState.checkNetworkState(app.applicationContext)) {
        getVarieties(pokemon)
        //  }
    }

    fun getVarieties(pokemon: Int) {
        coroutineScope.launch {
            getVarietiesFromApi(pokemon)
        }
    }
}
