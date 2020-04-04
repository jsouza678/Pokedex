package souza.home.com.pokedexapp.ui.details.other

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import souza.home.com.pokedexapp.network.PokeApi
import souza.home.com.pokedexapp.network.model.stats.PokemonProperty

enum class DetailsPokedexStatus{ LOADING, ERROR, DONE, EMPTY}

class PokeOthersViewModel(pokemon: String, app: Application): AndroidViewModel(app) {

    private var _status = MutableLiveData<DetailsPokedexStatus>()

    val status : LiveData<DetailsPokedexStatus>
        get() = _status

    private var _other = MutableLiveData<PokemonProperty>()

    val other : LiveData<PokemonProperty>
        get() = _other

    init {
        getOtherProperties(pokemon)
    }

    private fun getOtherProperties(pokemon: String) {

        _status.value = DetailsPokedexStatus.LOADING

        PokeApi.retrofitService.getPokeStats(pokemon).enqueue(object : Callback<PokemonProperty> {
            override fun onFailure(call: Call<PokemonProperty>, t: Throwable) {
                _status.value = DetailsPokedexStatus.ERROR
            }

            override fun onResponse(
                call: Call<PokemonProperty>,
                response: Response<PokemonProperty>
            ) {
                val item = response.body()



                try {
                    _other.value = item
                    _status.value = DetailsPokedexStatus.DONE
                } catch (e: Exception) {
                    // varietiesArray.add("No varieties")
                    _status.value = DetailsPokedexStatus.EMPTY
                }
            }

        })

    }
}