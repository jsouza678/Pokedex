package souza.home.com.pokedexapp.presentation.homefragment

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.domain.model.Poke
import souza.home.com.pokedexapp.domain.usecase.FetchPokesFromApi
import souza.home.com.pokedexapp.domain.usecase.GetPokesFromDatabase
import souza.home.com.pokedexapp.utils.Constants.Companion.ABSOLUTE_ZERO
import souza.home.com.pokedexapp.utils.Constants.Companion.DELAY_POST_400
import souza.home.com.pokedexapp.utils.Constants.Companion.POKE_LIMIT

class HomeViewModel(
    var getPokesFromDatabase: GetPokesFromDatabase,
    var fetchPokesFromApi: FetchPokesFromApi
): ViewModel() {

    private var isLoading: Boolean = false
    private var element: Int = ABSOLUTE_ZERO
    fun updatePokesListOnViewLiveData(): LiveData<List<Poke>?> = getPokesFromDatabase()
    //fun checkRequestStatus(): LiveData<HomePokedexStatus> = pokesRepository.internet
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    //private val pokesRepository = PokemonRepositoryImpl(app.applicationContext)
    //private var getPokesFromDatabase: GetPokesFromDatabase = GetPokesFromDatabase(pokesRepository)

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
