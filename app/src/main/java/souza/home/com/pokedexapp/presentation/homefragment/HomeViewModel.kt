package souza.home.com.pokedexapp.presentation.homefragment

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.domain.model.Poke
import souza.home.com.pokedexapp.domain.usecase.GetPokesFromApi
import souza.home.com.pokedexapp.domain.usecase.GetPokesFromDatabase
import souza.home.com.pokedexapp.utils.Constants.Companion.ABSOLUTE_ZERO
import souza.home.com.pokedexapp.utils.Constants.Companion.DELAY_POST_400
import souza.home.com.pokedexapp.utils.Constants.Companion.POKE_LIMIT

class HomeViewModel(
    private val getPokesFromDatabase: GetPokesFromDatabase,
    private val fetchPokesFromApi: GetPokesFromApi
): ViewModel() {

    private var isLoading: Boolean = false
    private var element: Int = ABSOLUTE_ZERO
    fun updatePokesListOnViewLiveData(): LiveData<List<Poke>?> = getPokesFromDatabase()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        getPokes()
    }

    private fun getPokes() {
        isLoading = true
        coroutineScope.launch {
            fetchPokesFromApi(element)
        }
        Handler().postDelayed({
            isLoading = false
        }, DELAY_POST_400)
    }

    fun onRecyclerViewScrolled(dy: Int, layoutManager: GridLayoutManager) {
        if (dy> ABSOLUTE_ZERO) {
            val isItTheListEnd = itIsTheListEnd(layoutManager = layoutManager)
            if (isLoading.not() && isItTheListEnd) {
                element += POKE_LIMIT // this will increase the elements and show the next page on API.
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
}
