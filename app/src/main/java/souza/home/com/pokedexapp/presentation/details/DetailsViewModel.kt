package souza.home.com.pokedexapp.presentation.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.data.pokedex.PropertiesPokedexStatus
import souza.home.com.pokedexapp.data.pokedex.PropertiesRepositoryImpl
import souza.home.com.pokedexapp.data.pokedex.VarietiesPokedexStatus
import souza.home.com.pokedexapp.data.pokedex.VarietiesRepositoryImpl
import souza.home.com.pokedexapp.domain.model.PokeProperty
import souza.home.com.pokedexapp.domain.model.PokeVariety

class DetailsViewModel(pokemon: Int, app: Application): AndroidViewModel(app) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val varietiesRepository = VarietiesRepositoryImpl(pokemon, app.applicationContext)
    private val propertiesRepository = PropertiesRepositoryImpl(pokemon, app.applicationContext)

    fun checkRequestPropertiesStatus(): LiveData<PropertiesPokedexStatus> = propertiesRepository.internet
    fun checkRequestVariationsStatus(): LiveData<VarietiesPokedexStatus> = varietiesRepository.internet
    fun updateVariationsOnViewLiveData(): LiveData<PokeVariety?>? = varietiesRepository.varieties
    fun updatePropertiesOnViewLiveData(): LiveData<PokeProperty>? = propertiesRepository.properties

    init{
        getColor(pokemon)
    }

    private fun getColor(pokemon: Int){
        coroutineScope.launch {
            varietiesRepository.refreshVarieties(pokemon)
            propertiesRepository.refreshProperties(pokemon)
        }
    }
}