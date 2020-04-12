package souza.home.com.pokedexapp.presentation.details.details_nested.others

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.AbilitiesRepositoryImpl
import souza.home.com.pokedexapp.data.pokedex.TypesRepositoryImpl
import souza.home.com.pokedexapp.domain.model.PokeAbility
import souza.home.com.pokedexapp.domain.model.PokeType
import java.lang.IllegalArgumentException

class OtherPropertiesViewModel(typeId: Int, abilityId: Int, app: Application): AndroidViewModel(app) {

    private var _statusAb = MutableLiveData<AbilityPokedexStatus>()

    val statusAb : LiveData<AbilityPokedexStatus>
        get() = _statusAb

    private val abilitiesRepository = AbilitiesRepositoryImpl(abilityId, app.applicationContext)
    private val typesRepository = TypesRepositoryImpl(typeId, app.applicationContext)

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    fun updateAbilityOnViewLiveData(): LiveData<PokeAbility>? = abilitiesRepository.abilities
    fun updateTypesOnViewLiveData(): LiveData<PokeType>? = typesRepository.types

    init{
        getAbilityData(abilityId)
        getPokesFromTypes(typeId)
    }

 /*   fun getAbilityDesc(abilityId: Int){
        getAbilityData(abilityId)
    }

    fun getPokesInTypes(typeId: Int){
        getPokesFromTypes(typeId)
    }*/

    private fun getAbilityData(abilityId: Int){
        _statusAb.value = AbilityPokedexStatus.LOADING

        coroutineScope.launch {
            abilitiesRepository.refreshAbilities(abilityId)
        }
    }

    private fun getPokesFromTypes(typeId: Int){

        _statusAb.value = AbilityPokedexStatus.LOADING

        coroutineScope.launch {
            typesRepository.refreshtypes(typeId)
        }
    }
}

class OtherViewModelFactory(val typeId: Int, val abilityId: Int, val app: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(OtherPropertiesViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return OtherPropertiesViewModel(typeId, abilityId, app) as T
        }
        throw IllegalArgumentException(app.applicationContext.getString(R.string.unknown_viewmodel))
    }
}

enum class AbilityPokedexStatus{ LOADING, ERROR, DONE}