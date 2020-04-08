package souza.home.com.pokedexapp.presentation.details

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

class DetailsPokedexViewModel(pokemon: String, app: Application): AndroidViewModel(app) {

    private var _status = MutableLiveData<DetailsPokedexStatus>()

    val status : LiveData<DetailsPokedexStatus>
        get() = _status

    private var _color = MutableLiveData<PokeRootVarieties>()

    val color : LiveData<PokeRootVarieties>
        get() = _color

    private var _poke = MutableLiveData<MutableList<String>>()

    val poke : LiveData<MutableList<String>>
        get() = _poke

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init{
        getColor(pokemon)
        getSprites(pokemon)
    }

    private fun getColor(pokemon: String){

        _status.value = DetailsPokedexStatus.LOADING

        coroutineScope.launch {
            val getColorDeferred = PokeApi.retrofitService.getVariations(pokemon)

            try{
                val item = getColorDeferred.await()

                try {
                    _color.value = item
                    _status.value = DetailsPokedexStatus.DONE
                } catch (e: Exception) {
                    _status.value = DetailsPokedexStatus.EMPTY
                }
            }catch(t: Throwable){
                _status.value = DetailsPokedexStatus.ERROR
            }
        }
    }

    private fun getSprites(pokemon: String){

        _status.value = DetailsPokedexStatus.LOADING

        coroutineScope.launch {
            val getSpritesDeferred = PokeApi.retrofitService.getPokeStats(pokemon)
            try{
                val listResult = getSpritesDeferred.await()
                val auxList = mutableListOf<String>()

                listResult.sprites.front_default?.let { auxList.add(it) }
                listResult.sprites.back_default?.let { auxList.add(it) }
                listResult.sprites.front_female?.let { auxList.add(it) }
                listResult.sprites.back_female?.let { auxList.add(it) }
                listResult.sprites.front_shiny?.let { auxList.add(it) }
                listResult.sprites.back_shiny?.let { auxList.add(it) }
                listResult.sprites.front_shiny_female?.let { auxList.add(it) }
                listResult.sprites.back_shiny_female?.let { auxList.add(it) }

                _poke.value = auxList
                _status.value = DetailsPokedexStatus.DONE

            }catch(t: Throwable){
                _status.value = DetailsPokedexStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Job().cancel()
    }

}
