package souza.home.com.pokedexapp.presentation.home

import android.app.Application
import android.os.Handler
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.HomePokedexStatus
import souza.home.com.pokedexapp.data.pokedex.PokemonRepositoryImpl
import souza.home.com.pokedexapp.domain.model.Poke
import souza.home.com.pokedexapp.utils.Constants.Companion.ABSOLUTE_ZERO
import souza.home.com.pokedexapp.utils.Constants.Companion.DELAY_POST_400
import souza.home.com.pokedexapp.utils.Constants.Companion.POKE_LIMIT

class HomeViewModel(app: Application) : AndroidViewModel(app) {

    private var isLoading: Boolean = false
    private var element: Int = ABSOLUTE_ZERO
    fun updatePokesListOnViewLiveData(): LiveData<List<Poke>?> = pokesRepository.pokes
    fun checkRequestStatus(): LiveData<HomePokedexStatus> = pokesRepository.internet
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val pokesRepository = PokemonRepositoryImpl(app.applicationContext)

    init {
        getPokes()
    }

    private fun getPokes() {
        isLoading = true
        coroutineScope.launch {
            pokesRepository.refreshPokes(element)
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

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(app) as T
            }
            throw IllegalArgumentException(app.applicationContext.getString(R.string.unknown_viewmodel))
        }
    }
}
