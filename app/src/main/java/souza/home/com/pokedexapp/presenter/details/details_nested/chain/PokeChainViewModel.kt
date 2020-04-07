package souza.home.com.pokedexapp.presenter.details.details_nested.chain

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import souza.home.com.pokedexapp.network.PokeApi
import souza.home.com.pokedexapp.network.model.evolution_chain.PokeEvolution
import souza.home.com.pokedexapp.network.model.evolution_chain.PokeEvolutionChain
import souza.home.com.pokedexapp.network.model.varieties.PokeRootVarieties

enum class DetailsPokedexStatus{ LOADING, ERROR, DONE, EMPTY}

class PokeChainViewModel(pokemon: String, app: Application): AndroidViewModel(app) {

    private var _status = MutableLiveData<DetailsPokedexStatus>()

    val status : LiveData<DetailsPokedexStatus>
        get() = _status

    private var _chain = MutableLiveData<MutableList<PokeEvolution>>()

    val chain : LiveData<MutableList<PokeEvolution>>
        get() = _chain


    init {
        getPokeChainUrl(pokemon)
    }

    private fun getPokeChainUrl(pokemon: String){

        _status.value = DetailsPokedexStatus.LOADING

        PokeApi.retrofitService.getVariations(pokemon).enqueue(object: Callback<PokeRootVarieties> {
            override fun onFailure(call: Call<PokeRootVarieties>, t: Throwable) {
                _status.value = DetailsPokedexStatus.ERROR
            }

            override fun onResponse(call: Call<PokeRootVarieties>, response: Response<PokeRootVarieties>) {
                val items = response.body()
                val pokeId = items?.evolution_chain?.url?.substringAfterLast("n/")?.substringBeforeLast("/")
                try {
                    getChainEvolution(pokeId!!)
                } catch (e: Exception) {
                    // varietiesArray.add("No varieties")
                    _status.value = DetailsPokedexStatus.EMPTY
                }

            }
        }
        )
    }

    private fun getChainEvolution(pokemon: String){
        _status.value = DetailsPokedexStatus.LOADING
        PokeApi.retrofitService.getEvolutionChain(pokemon).enqueue(object:
            Callback<PokeEvolutionChain> {
            override fun onFailure(call: Call<PokeEvolutionChain>, t: Throwable) {
                _status.value = DetailsPokedexStatus.ERROR
            }

            override fun onResponse(call: Call<PokeEvolutionChain>, response: Response<PokeEvolutionChain>) {
                val item = response.body()
                val evolutionArray : List<PokeEvolution>

                evolutionArray = ArrayList()
                evolutionArray.clear()


                if(item?.chain?.species?.name != null){  //// 1 CHAIN

                    evolutionArray.add(item.chain)

                    try{
                        evolutionArray.add(item.chain.evolves_to!![0])
                        try {
                            evolutionArray.add(item.chain.evolves_to!![0].evolves_to!![0])

                        }catch (e: Exception){

                        }
                        }
                        catch (e: Exception) {

                        }

                    }

                try {
                    _chain.value = evolutionArray

                    _status.value = DetailsPokedexStatus.DONE
                } catch (e: Exception) {
                    _status.value = DetailsPokedexStatus.EMPTY
                }

            }

        })

    }
}