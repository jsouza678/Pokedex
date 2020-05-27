package com.souza.pokedetail.presentation.pokedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.souza.pokedetail.domain.model.PokeProperty
import com.souza.pokedetail.domain.model.PokeVariety
import com.souza.pokedetail.domain.usecase.FetchPropertiesFromApi
import com.souza.pokedetail.domain.usecase.FetchVarietiesFromApi
import com.souza.pokedetail.domain.usecase.GetPropertiesFromDatabase
import com.souza.pokedetail.domain.usecase.GetVarietiesFromDatabase
import com.souza.pokedetail.utils.Constants.Companion.LIMIT_NORMAL_POKES
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokeDetailsViewModel(
    private val pokemonId: Int,
    private val fetchVarietiesFromApi: FetchVarietiesFromApi,
    private val getVarietiesFromDatabase: GetVarietiesFromDatabase,
    private val fetchPropertiesFromApi: FetchPropertiesFromApi,
    private val getPropertiesFromDatabase: GetPropertiesFromDatabase
) : ViewModel() {

    private val coroutineScope = Dispatchers.IO
    fun updateVariationsOnViewLiveData(): LiveData<PokeVariety?>? = getVarietiesFromDatabase(pokemonId)
    fun updatePropertiesOnViewLiveData(): LiveData<PokeProperty>? = getPropertiesFromDatabase(pokemonId)
    internal var isNormalPoke = MutableLiveData<Unit>()
    internal var isEvolutionPoke = MutableLiveData<Unit>()

    init {
        getColor(pokemonId)

        if (pokemonId < LIMIT_NORMAL_POKES) { isNormalPoke.postValue(Unit) } else { isEvolutionPoke.postValue(Unit) }
    }

    private fun getColor(pokemon: Int) {
        viewModelScope.launch(coroutineScope) {
            fetchVarietiesFromApi(pokemon)
            fetchPropertiesFromApi(pokemon)
        }
    }
}
