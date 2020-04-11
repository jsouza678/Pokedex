package souza.home.com.pokedexapp.presentation.details

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.data.pokedex.PropertiesRepositoryImpl
import souza.home.com.pokedexapp.data.pokedex.VarietiesRepositoryImpl
import souza.home.com.pokedexapp.data.remote.PokeApi
import souza.home.com.pokedexapp.domain.model.PokeProperty
import souza.home.com.pokedexapp.domain.model.PokeVariety

class DetailsPokedexViewModel(pokemon: Int, app: Application): AndroidViewModel(app) {

    private var _status = MutableLiveData<DetailsPokedexStatus>()

    val status : LiveData<DetailsPokedexStatus>
        get() = _status

    private var _color = MutableLiveData<PokeVariety>()

    val color : LiveData<PokeVariety>
        get() = _color

    private var _poke = MutableLiveData<MutableList<String>>()

    val poke : LiveData<MutableList<String>>
        get() = _poke

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val varietiesRepository =
        VarietiesRepositoryImpl(pokemon, app.applicationContext)

    private val propertiesRepository =
        PropertiesRepositoryImpl(pokemon, app.applicationContext)

    private val conectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val activeNetwork : NetworkInfo? = conectivityManager.activeNetworkInfo
    private val isConnected : Boolean = activeNetwork?.isConnected == true
    fun updateVariationsOnViewLiveData(): LiveData<PokeVariety>? = varietiesRepository.varieties
    fun updatePropertiesOnViewLiveData(): LiveData<PokeProperty>? = propertiesRepository.properties

//
    init{
        if(isConnected){
            getColor(pokemon)
        }
        getSprites(pokemon)
    }

    private fun getColor(pokemon: Int){
        coroutineScope.launch {
            varietiesRepository.refreshVarieties(pokemon)
        }
    }

    private fun getSprites(pokemon: Int){

        _status.value = DetailsPokedexStatus.LOADING

        coroutineScope.launch {
            propertiesRepository.refreshProperties(pokemon)
        }
    }
}

enum class DetailsPokedexStatus{ LOADING, ERROR, DONE, EMPTY}