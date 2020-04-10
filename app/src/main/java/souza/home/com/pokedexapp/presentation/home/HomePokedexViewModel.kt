package souza.home.com.pokedexapp.presentation.home

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.data.pokedex.PokemonRepositoryImpl
import souza.home.com.pokedexapp.data.pokedex.local.getDatabase
import souza.home.com.pokedexapp.data.pokedex.remote.model.Poke
import java.lang.IllegalArgumentException

enum class HomePokedexStatus{ LOADING, ERROR, DONE, EMPTY}

class HomePokedexViewModel(app: Application) : AndroidViewModel(app){

    private var isLoading : Boolean = false

    private var page : Int = 0

    private val _status = MutableLiveData<HomePokedexStatus>()

    val status : LiveData<HomePokedexStatus>
    get() = _status

    fun updatePokeslListOnViewLiveData(): LiveData<List<Poke>> = pokesRepository.pokes

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database =
        getDatabase(app.applicationContext)
    private val pokesRepository =
        PokemonRepositoryImpl(database)

    private val conectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val activeNetwork : NetworkInfo? = conectivityManager.activeNetworkInfo
    private val isConnected : Boolean = activeNetwork?.isConnected == true

    init{
        getPokes()
    }

    fun getPokes(){
        _status.value = HomePokedexStatus.LOADING
        if(isConnected)(
                try{
                    coroutineScope.launch {
                        pokesRepository.refreshPokes(page)
                    }
                    _status.value = HomePokedexStatus.DONE
                }catch(e: Exception){
                    _status.value = HomePokedexStatus.ERROR
                })
    }

    fun onRecyclerViewScrolled(dy: Int, layoutManager: GridLayoutManager){
        if(dy>0){
            val isItTheListEnd = itIsTheListEnd(layoutManager = layoutManager)
            if(isLoading.not() && isItTheListEnd){
                page+=20
                //getPage(page)
                getPokes()
            }
        }
    }

    fun itIsTheListEnd(layoutManager: GridLayoutManager) : Boolean{
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        return visibleItemCount + firstVisibleItemPosition >= totalItemCount
    }

    override fun onCleared() {
        super.onCleared()
        Job().cancel()
    }

    class Factory(val app: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(HomePokedexViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return HomePokedexViewModel(app) as T
            }
            throw IllegalArgumentException("Viewmodel unknown")
        }
    }

}