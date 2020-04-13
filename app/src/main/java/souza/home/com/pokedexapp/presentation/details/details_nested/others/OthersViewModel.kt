package souza.home.com.pokedexapp.presentation.details.details_nested.others

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
import souza.home.com.pokedexapp.domain.model.PokeProperty

enum class DetailsPokedexStatus{ LOADING, ERROR, DONE, EMPTY}

class OthersViewModel(pokemon: Int, app: Application): AndroidViewModel(app) {

    private var _status = MutableLiveData<DetailsPokedexStatus>()

    val status : LiveData<DetailsPokedexStatus>
        get() = _status

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val propertyRepository =
        PropertiesRepositoryImpl(pokemon, app.applicationContext)

    private val conectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val activeNetwork : NetworkInfo? = conectivityManager.activeNetworkInfo
    private val isConnected : Boolean = activeNetwork?.isConnected == true

    fun updateAbilitiesOnViewLiveData(): LiveData<PokeProperty>? = propertyRepository.properties

    init {
         getOtherProperties(pokemon)
    }

    private fun getOtherProperties(pokemon: Int) {
        //_status.value = DetailsPokedexStatus.LOADING
        coroutineScope.launch {
            propertyRepository.refreshProperties(pokemon)
        }
    }
}