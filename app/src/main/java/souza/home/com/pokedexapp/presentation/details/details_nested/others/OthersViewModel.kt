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
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.data.pokedex.AbilitiesRepositoryImpl
import souza.home.com.pokedexapp.data.pokedex.PropertiesRepositoryImpl
import souza.home.com.pokedexapp.data.pokedex.TypesRepositoryImpl
import souza.home.com.pokedexapp.data.pokedex.remote.PokeApi
import souza.home.com.pokedexapp.data.pokedex.remote.model.response.PropertyResponse
import souza.home.com.pokedexapp.data.pokedex.remote.model.response.NestedType
import souza.home.com.pokedexapp.domain.model.PokeAbility
import souza.home.com.pokedexapp.domain.model.PokeProperty
import souza.home.com.pokedexapp.domain.model.PokeType
import souza.home.com.pokedexapp.domain.model.PokeVariety

enum class DetailsPokedexStatus{ LOADING, ERROR, DONE, EMPTY}

enum class AbilityPokedexStatus{ LOADING, ERROR, DONE}

class OthersViewModel(pokemon: Int, app: Application): AndroidViewModel(app) {

    private var _status = MutableLiveData<DetailsPokedexStatus>()

    val status : LiveData<DetailsPokedexStatus>
        get() = _status

    private var _statusAb = MutableLiveData<AbilityPokedexStatus>()

    val statusAb : LiveData<AbilityPokedexStatus>
        get() = _statusAb

    private var _abilityDesc = MutableLiveData<String>()

    val abilityDesc : LiveData<String>
        get() = _abilityDesc

    private var _pokeTypes = MutableLiveData<MutableList<NestedType>>()

    val pokeTypes : LiveData<MutableList<NestedType>>
        get() = _pokeTypes

    private var _other = MutableLiveData<PropertyResponse>()

    val other : LiveData<PropertyResponse>
        get() = _other

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val abilitiesRepository =
        AbilitiesRepositoryImpl(pokemon, app.applicationContext)
    private val typesRepository =
        TypesRepositoryImpl(pokemon, app.applicationContext)
    private val propertyRepository =
        PropertiesRepositoryImpl(pokemon, app.applicationContext)

    private val conectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val activeNetwork : NetworkInfo? = conectivityManager.activeNetworkInfo
    private val isConnected : Boolean = activeNetwork?.isConnected == true

    fun updateAbilityOnViewLiveData(): LiveData<PokeAbility>? = abilitiesRepository.abilities
    fun updateTypesOnViewLiveData(): LiveData<PokeType>? = typesRepository.types
    fun updateStatsOnViewLiveData(): LiveData<PokeProperty>? = propertyRepository.properties


    init {
        //_pokeTypes.value = mutableListOf()
        // getOtherProperties(pokemon)
    }

    private fun getOtherProperties(pokemon: Int) {

        _status.value = DetailsPokedexStatus.LOADING

        coroutineScope.launch {
            propertyRepository.refreshProperties(pokemon)

           /* val getStatsDeferred = PokeApi.retrofitService.getPokeStats(pokemon)
            try{
                val listResult = getStatsDeferred.await()

                _other.value = listResult
                _status.value = DetailsPokedexStatus.DONE

            }catch(t: Throwable){
                _status.value = DetailsPokedexStatus.ERROR
            }*/
        }
    }

    fun getAbilityDesc(abilityId: Int){
        getAbilityData(abilityId)
    }

    fun getPokesInTypes(typeId: Int){
        getPokesFromTypes(typeId)
    }

    private fun getAbilityData(abilityId: Int){
        _statusAb.value = AbilityPokedexStatus.LOADING

        coroutineScope.launch {
            abilitiesRepository.refreshAbilities(abilityId)
         /*   val getAbilityDeferred = PokeApi.retrofitService.getAbilityData(abilityId)

            try{
                val abilityData = getAbilityDeferred.await()

                _abilityDesc.value = abilityData.effect?.get(0)?.effect

                _statusAb.value = AbilityPokedexStatus.DONE

            }catch(t: Throwable){
                _statusAb.value = AbilityPokedexStatus.ERROR
            }*/
        }
    }

    private fun getPokesFromTypes(typeId: Int){

        _statusAb.value = AbilityPokedexStatus.LOADING

        coroutineScope.launch {
            typesRepository.refreshtypes(typeId)
            /*val getTypesDeferred = PokeApi.retrofitService.getTypeData(typeId)

            try{
                val typesData = getTypesDeferred.await()

                _pokeTypes.value = typesData.pokemon

                _statusAb.value = AbilityPokedexStatus.DONE
            }catch(t: Throwable){
                _statusAb.value = AbilityPokedexStatus.ERROR
            }*/
        }
    }

    override fun onCleared() {
        super.onCleared()
        Job().cancel()
    }
}