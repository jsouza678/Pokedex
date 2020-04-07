package souza.home.com.pokedexapp.presenter.details.details_nested.other

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import souza.home.com.pokedexapp.network.PokeApi
import souza.home.com.pokedexapp.network.model.ability.PokeAbilityRoot
import souza.home.com.pokedexapp.network.model.stats.PokemonProperty
import souza.home.com.pokedexapp.network.model.types.PokeTypeRoot
import souza.home.com.pokedexapp.network.model.types.PokemonNested

enum class DetailsPokedexStatus{ LOADING, ERROR, DONE, EMPTY}

enum class AbilityPokedexStatus{ LOADING, ERROR, DONE, EMPTY}

class PokeOthersViewModel(pokemon: String, app: Application): AndroidViewModel(app) {

    private var _status = MutableLiveData<DetailsPokedexStatus>()

    val status : LiveData<DetailsPokedexStatus>
        get() = _status

    private var _statusAb = MutableLiveData<AbilityPokedexStatus>()

    val statusAb : LiveData<AbilityPokedexStatus>
        get() = _statusAb

    private var _abilityDesc = MutableLiveData<String>()

    val abilityDesc : LiveData<String>
        get() = _abilityDesc

    private var _pokeTypes = MutableLiveData<MutableList<PokemonNested>>()

    val pokeTypes : LiveData<MutableList<PokemonNested>>
        get() = _pokeTypes

    private var _other = MutableLiveData<PokemonProperty>()

    val other : LiveData<PokemonProperty>
        get() = _other

    init {
        _pokeTypes.value = mutableListOf()
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


    fun getAbilityDesc(abId: String){
        getAbilityData(abId)
    }



    fun getPokesInTypes(typeId: String){
        getPokesFromTypes(typeId)
    }


    fun getAbilityData(abId: String){
        var desc : String = ""
        _statusAb.value = AbilityPokedexStatus.LOADING

        PokeApi.retrofitService.getAbilityData(abId).enqueue(object : Callback<PokeAbilityRoot> {
            override fun onFailure(call: Call<PokeAbilityRoot>, t: Throwable) {
                _statusAb.value = AbilityPokedexStatus.ERROR
            }
            override fun onResponse(
                call: Call<PokeAbilityRoot>,
                response: Response<PokeAbilityRoot>
            ) {
                //desc = response.body()?.effect?.get(0)?.effect!!
                _abilityDesc.value = response.body()?.effect?.get(0)?.effect

                _statusAb.value = AbilityPokedexStatus.DONE
            }
        })
    }

    private fun getPokesFromTypes(typeId: String){

        _statusAb.value = AbilityPokedexStatus.LOADING

        PokeApi.retrofitService.getTypeData(typeId).enqueue(object : Callback<PokeTypeRoot> {
            override fun onFailure(call: Call<PokeTypeRoot>, t: Throwable) {
                _statusAb.value = AbilityPokedexStatus.ERROR
            }

            override fun onResponse(call: Call<PokeTypeRoot>, response: Response<PokeTypeRoot>) {

                _pokeTypes.value = response.body()?.pokemon!!

                _statusAb.value = AbilityPokedexStatus.DONE
            }

        })
    }
}