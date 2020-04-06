package souza.home.com.pokedexapp.ui.details

import android.app.Application
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.request.RequestOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.network.PokeApi
import souza.home.com.pokedexapp.network.model.stats.PokeSprites
import souza.home.com.pokedexapp.network.model.stats.PokemonProperty
import souza.home.com.pokedexapp.network.model.varieties.PokeColor
import souza.home.com.pokedexapp.network.model.varieties.PokeRootVarieties

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


    init{
        getColor(pokemon)
        getSprites(pokemon)
    }

    private fun getColor(pokemon: String){

        _status.value = DetailsPokedexStatus.LOADING

        PokeApi.retrofitService.getVariations(pokemon).enqueue(object: Callback<PokeRootVarieties> {
            override fun onFailure(call: Call<PokeRootVarieties>, t: Throwable) {
                _status.value = DetailsPokedexStatus.ERROR
            }

            override fun onResponse(call: Call<PokeRootVarieties>, response: Response<PokeRootVarieties>) {
                val items = response.body()

                try {
                    _color.value = items
                    //_status.value = DetailsPokedexStatus.DONE
                } catch (e: Exception) {
                    // varietiesArray.add("No varieties")
                   // _status.value = DetailsPokedexStatus.EMPTY
                }
            }
        })
    }

    fun getSprites(pokemon: String){
        _status.value = DetailsPokedexStatus.LOADING

        PokeApi.retrofitService.getPokeStats(pokemon).enqueue(object : Callback<PokemonProperty> {
            override fun onFailure(call: Call<PokemonProperty>, t: Throwable) {
                _status.value = DetailsPokedexStatus.ERROR
            }

            override fun onResponse(call: Call<PokemonProperty>, response: Response<PokemonProperty>) {

                val items = response.body()
                var auxList2 = mutableListOf<String>()

                items?.sprites?.front_default?.let { auxList2.add(it) }
                items?.sprites?.back_default?.let { auxList2.add(it) }
                items?.sprites?.front_female?.let { auxList2.add(it) }
                items?.sprites?.back_female?.let { auxList2.add(it) }
                items?.sprites?.front_shiny?.let { auxList2.add(it) }
                items?.sprites?.back_shiny?.let { auxList2.add(it) }
                items?.sprites?.front_shiny_female?.let { auxList2.add(it) }
                items?.sprites?.back_shiny_female?.let { auxList2.add(it) }
/*


                var auxList = mutableListOf(
                    items?.sprites?.front_default,
                    items?.sprites?.back_default,
                    items?.sprites?.front_female,
                    items?.sprites?.back_female,
                    items?.sprites?.front_shiny,
                    items?.sprites?.back_shiny,
                    items?.sprites?.front_shiny_female,
                    items?.sprites?.back_shiny_female)
*/


                    _poke.value = auxList2
                    _status.value = DetailsPokedexStatus.DONE

            }
        })
    }

}
