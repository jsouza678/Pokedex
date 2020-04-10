package souza.home.com.pokedexapp.presentation.details.details_nested.about

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.data.pokedex.VarietiesRepositoryImpl
import souza.home.com.pokedexapp.data.pokedex.local.getVarietiesDatabase
import souza.home.com.pokedexapp.data.pokedex.remote.model.varieties.PokeVarietiesResponse
import souza.home.com.pokedexapp.data.pokedex.remote.model.PokeVariety
import souza.home.com.pokedexapp.domain.model.asDomainModelFromVariations
import java.lang.Exception


enum class DetailsPokedexStatus{ LOADING, ERROR, DONE, EMPTY}

class PokeAboutViewModel(pokemon: String, app: Application): AndroidViewModel(app) {

    private var _status = MutableLiveData<DetailsPokedexStatus>()

    val status : LiveData<DetailsPokedexStatus>
        get() = _status

    private var _varieties = MutableLiveData<PokeVariety>()

    val varietiesResponse : LiveData<PokeVariety>
        get() = _varieties

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun updateVariationsOnViewLiveData(): LiveData<PokeVariety>? = varietiesRepository.varieties

    private val database =
        getVarietiesDatabase(app)
    private val varietiesRepository =
        VarietiesRepositoryImpl(database, Integer.parseInt(pokemon))

    private val conectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val activeNetwork : NetworkInfo? = conectivityManager.activeNetworkInfo
    private val isConnected : Boolean = activeNetwork?.isConnected == true

    init {
        if(isConnected){
                getVarieties(pokemon)
        }

    }

    fun getVarieties(pokemon: String){
        coroutineScope.launch {
            varietiesRepository.refreshVarieties(pokemon)
        }
        /*try{
            _varieties.value = database.varietiesDao.getVar(Integer.parseInt(pokemon)).asDomainModelFromVariations()
        }catch (e: Exception){
            Log.i("error", "message " + e.message!!)
        }*/
        DetailsPokedexStatus.DONE
    }
  /*  private fun getVarieties(pokemon: String){

        _status.value = DetailsPokedexStatus.LOADING

        coroutineScope.launch {
        val getVarietiesDeferred = PokeApi.retrofitService.getVariations(pokemon)
            try{
                val listResult = getVarietiesDeferred.await()

                try {
                    _varieties.value = listResult
                    _status.value = DetailsPokedexStatus.DONE
                } catch (e: Exception) {
                    // varietiesArray.add("No varietiesResponse")
                    _status.value = DetailsPokedexStatus.EMPTY
                }
            }catch(t: Throwable){
                _status.value = DetailsPokedexStatus.ERROR
            }

        }
    }*/


    override fun onCleared() {
        super.onCleared()
        Job().cancel()
    }
}