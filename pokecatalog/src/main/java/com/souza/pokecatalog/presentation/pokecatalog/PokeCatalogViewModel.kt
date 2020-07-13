package com.souza.pokecatalog.presentation.pokecatalog

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import com.souza.pokecatalog.domain.model.Pokemon
import com.souza.pokecatalog.domain.usecase.FetchPokesFromApi
import com.souza.pokecatalog.domain.usecase.GetPokesFromDatabase
import com.souza.pokecatalog.utils.Constants.Companion.ABSOLUTE_ZERO
import com.souza.pokecatalog.utils.Constants.Companion.DELAY_MEDIUM
import com.souza.pokecatalog.utils.Constants.Companion.POKE_LIMIT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokeCatalogViewModel(
    internal val getPokesFromDatabase: GetPokesFromDatabase,
    internal val fetchPokesFromApi: FetchPokesFromApi
) : ViewModel() {

    internal var isLoading: Boolean = false
    internal var page: Int = ABSOLUTE_ZERO
    internal val coroutineScope = Dispatchers.IO
    internal var hasNetworkConnectivity = true
    internal var turnOnProgressBar = MutableLiveData<Unit>()
    internal var turnOffProgressBar = MutableLiveData<Unit>()
    internal var checkEndOfList = MutableLiveData<Unit>()

    fun updatePokesListOnViewLiveData(): LiveData<List<Pokemon>?> = getPokesFromDatabase()
    fun turnOnProgressBarOnLiveData(): LiveData<Unit> = turnOnProgressBar
    fun turnOffProgressBarOnLiveData(): LiveData<Unit> = turnOffProgressBar

    fun getPokes() {
        if (hasNetworkConnectivity.not()) return
        isLoading = true
        turnOnProgressBar.postValue(Unit)
        viewModelScope.launch(context = coroutineScope) {
            fetchPokesFromApi(page)
        }
        isLoading = false
        Handler().postDelayed({
            turnOffProgressBar.postValue(Unit)
        }, DELAY_MEDIUM)
    }

    fun loadOnRecyclerViewScrolled(dy: Int, layoutManager: GridLayoutManager) {
        if (dy> ABSOLUTE_ZERO) {
            val isItTheListEnd = itIsTheListEnd(layoutManager = layoutManager)
            if (isLoading.not() && isItTheListEnd && hasNetworkConnectivity) {
                getPokes()
            }
        }
    }

    internal fun itIsTheListEnd(layoutManager: GridLayoutManager): Boolean {
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        return visibleItemCount + firstVisibleItemPosition >= totalItemCount
    }

    internal fun updateConnectivityStatus(hasNetworkConnectivity: Boolean) {
        this.hasNetworkConnectivity = hasNetworkConnectivity
    }
}
