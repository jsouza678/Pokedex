package souza.home.com.pokedexapp.presentation.details.details_nested.about

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.data.pokedex.VarietiesRepositoryImpl
import souza.home.com.pokedexapp.domain.model.PokeVariety

class AboutViewModel(pokemon: Int, app: Application): AndroidViewModel(app) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun updateVariationsOnViewLiveData(): LiveData<PokeVariety>? = varietiesRepository.varieties

    private val varietiesRepository =
        VarietiesRepositoryImpl(pokemon, app.applicationContext)

    private val conectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val activeNetwork : NetworkInfo? = conectivityManager.activeNetworkInfo
    private val isConnected : Boolean = activeNetwork?.isConnected == true

    init {
        if(isConnected){
            getVarieties(pokemon)
        }
    }

    fun getVarieties(pokemon: Int){

        coroutineScope.launch {
            varietiesRepository.refreshVarieties(pokemon)
        }
    }
}