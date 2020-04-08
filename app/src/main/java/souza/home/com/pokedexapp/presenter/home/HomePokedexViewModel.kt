package souza.home.com.pokedexapp.presenter.home

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Handler
import android.util.Log
import androidx.lifecycle.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import souza.home.com.pokedexapp.network.PokeApi
import souza.home.com.pokedexapp.network.PokeRootProperty
import souza.home.com.pokedexapp.network.model.main_model.Pokemon
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.network.model.main_model.Repo
import souza.home.com.pokedexapp.network.model.main_model.getDatabase
import java.lang.IllegalArgumentException

enum class HomePokedexStatus{ LOADING, ERROR, DONE, EMPTY}

class HomePokedexViewModel(app: Application) : AndroidViewModel(app){

    private var isLoading : Boolean = false

    private var page : Int = 0

    private val _poke = MutableLiveData<MutableList<Pokemon>>()

    val poke : LiveData<MutableList<Pokemon>>
            get() = _poke

    private val _status = MutableLiveData<HomePokedexStatus>()

    val status : LiveData<HomePokedexStatus>
    get() = _status

    fun updatePokeslListOnViewLiveData(): LiveData<List<Pokemon>> = pokesRepository.pokes

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getDatabase(app)
    private val pokesRepository = Repo(database)

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
                    _poke.value = mutableListOf()
                    coroutineScope.launch {
                        pokesRepository.refreshPokes(page)
                    }
                    _status.value = HomePokedexStatus.DONE
                }catch(e: Exception){
                    _status.value = HomePokedexStatus.ERROR
                }

                )

    }

//    var pokess = pokesRepository.pokes.value!![1]

   /* private fun getPokes(){

        _status.value = HomePokedexStatus.LOADING

        coroutineScope.launch {
            var getPokesDeferred = PokeApi.retrofitService.getPokes(page)
            try{
                val listResult = getPokesDeferred.await() // await is non blocking... without blocking the main thread.

                _poke.value?.addAll(listResult.results!!)

                if(_poke.value.isNullOrEmpty()){
                    _status.value = HomePokedexStatus.EMPTY
                }else{
                   *//* val r = Runnable {*//*
                        _status.value = HomePokedexStatus.DONE
*//*                    }
                    Handler().postDelayed(r, 500)*//*

                }
            }catch(t: Throwable){
                _status.value = HomePokedexStatus.ERROR
            }
        }

    }*/

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