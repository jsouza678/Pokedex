package souza.home.com.pokedexapp.presentation.details.details_nested.about

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.data.pokedex.VarietiesRepositoryImpl
import souza.home.com.pokedexapp.domain.model.PokeVariety
import souza.home.com.pokedexapp.utils.CheckNetworkState

class AboutViewModel(pokemon: Int, app: Application) : AndroidViewModel(app) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    fun updateVariationsOnViewLiveData(): LiveData<PokeVariety?>? = varietiesRepository.varieties

    private val varietiesRepository =
        VarietiesRepositoryImpl(pokemon, app.applicationContext)

    init {
        if (CheckNetworkState.checkNetworkState(app.applicationContext)) {
            getVarieties(pokemon)
        }
    }

    fun getVarieties(pokemon: Int) {

        coroutineScope.launch {
            varietiesRepository.refreshVarieties(pokemon)
        }
    }
}
