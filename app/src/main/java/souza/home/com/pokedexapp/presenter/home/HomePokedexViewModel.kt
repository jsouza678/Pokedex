package souza.home.com.pokedexapp.presenter.home

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import souza.home.com.pokedexapp.network.PokeApi
import souza.home.com.pokedexapp.network.PokeRootProperty
import souza.home.com.pokedexapp.network.model.main_model.Pokemon
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class HomePokedexStatus{ LOADING, ERROR, DONE, EMPTY}

class HomePokedexViewModel : ViewModel(){

    private var isLoading : Boolean = false

    private var page : Int = 0

    private val _poke = MutableLiveData<MutableList<Pokemon>>()

    val poke : LiveData<MutableList<Pokemon>>
            get() = _poke

    private val _status = MutableLiveData<HomePokedexStatus>()

    val status : LiveData<HomePokedexStatus>
    get() = _status

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    init{
        _poke.value = mutableListOf()
        getPokes()
    }

    private fun getPokes(){

        _status.value = HomePokedexStatus.LOADING

        coroutineScope.launch {
            var getPokesDeferred = PokeApi.retrofitService.getPokes(page)
            try{
                val listResult = getPokesDeferred.await() // await is non blocking... without blocking the main thread.

                _poke.value?.addAll(listResult.results!!)

              /*  for (i in 0 until length!!) {
                    _poke.value?.add((response.body()?.results!![i]))
                }*/

                if(_poke.value.isNullOrEmpty()){
                    _status.value = HomePokedexStatus.EMPTY
                }else{
                   /* val r = Runnable {*/
                        _status.value = HomePokedexStatus.DONE
/*                    }
                    Handler().postDelayed(r, 500)*/

                }
            }catch(t: Throwable){
                _status.value = HomePokedexStatus.ERROR
            }
        }

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
}