package souza.home.com.pokecatalog.presentation.pokecatalog

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import souza.home.com.pokecatalog.domain.model.Pokemon
import souza.home.com.pokecatalog.domain.usecase.FetchPokesFromApi
import souza.home.com.pokecatalog.domain.usecase.GetPokesFromDatabase
import souza.home.com.pokecatalog.utils.Constants.Companion.ABSOLUTE_ZERO
import souza.home.com.pokecatalog.utils.Constants.Companion.DELAY_MEDIUM
import souza.home.com.pokecatalog.utils.Constants.Companion.POKE_LIMIT

class PokeCatalogViewModel(
    private val getPokesFromDatabase: GetPokesFromDatabase,
    private val fetchPokesFromApi: FetchPokesFromApi
) : ViewModel() {

    private var isLoading: Boolean = false
    private var element: Int = ABSOLUTE_ZERO
    fun updatePokesListOnViewLiveData(): LiveData<List<Pokemon>?> = getPokesFromDatabase()
    private val coroutineScope = Dispatchers.IO
    private var hasNetworkConnectivity = true
    internal var turnOnProgressBar = MutableLiveData<Unit>()
    internal var turnOffProgressBar = MutableLiveData<Unit>()
    internal var checkEndOfList = MutableLiveData<Unit>()

    init {
        getPokes()
    }

    private fun getPokes() {
        if (hasNetworkConnectivity.not()) return
        isLoading = true
        turnOnProgressBar.postValue(Unit)
        viewModelScope.launch(coroutineScope) {
            fetchPokesFromApi(element)
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
                    element += POKE_LIMIT
                    getPokes()
            }
        }
    }

    private fun itIsTheListEnd(layoutManager: GridLayoutManager): Boolean {
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        return visibleItemCount + firstVisibleItemPosition >= totalItemCount
    }

    fun updateConnectivityStatus(hasNetworkConnectivity: Boolean) {
        this.hasNetworkConnectivity = hasNetworkConnectivity
    }
}
