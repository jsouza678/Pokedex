package souza.home.com.pokedexapp.presentation.details.details_nested.about

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.data.remote.PokeApi
import souza.home.com.pokedexapp.data.pokedex.remote.model.varieties.PokeRootVarieties


enum class DetailsPokedexStatus{ LOADING, ERROR, DONE, EMPTY}

class PokeAboutViewModel(pokemon: String, app: Application): AndroidViewModel(app) {

    private var _status = MutableLiveData<DetailsPokedexStatus>()

    val status : LiveData<DetailsPokedexStatus>
        get() = _status

    private var _varieties = MutableLiveData<PokeRootVarieties>()

    val varieties : LiveData<PokeRootVarieties>
        get() = _varieties

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getVarieties(pokemon)
    }

    private fun getVarieties(pokemon: String){

        _status.value = DetailsPokedexStatus.LOADING

        coroutineScope.launch {
        val getVarietiesDeferred = PokeApi.retrofitService.getVariations(pokemon)
            try{
                val listResult = getVarietiesDeferred.await()

                try {
                    _varieties.value = listResult
                    _status.value = DetailsPokedexStatus.DONE
                } catch (e: Exception) {
                    // varietiesArray.add("No varieties")
                    _status.value = DetailsPokedexStatus.EMPTY
                }
            }catch(t: Throwable){
                _status.value = DetailsPokedexStatus.ERROR
            }

        }
    }
}