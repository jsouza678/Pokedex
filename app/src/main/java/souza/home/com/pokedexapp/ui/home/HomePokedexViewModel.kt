package souza.home.com.pokedexapp.ui.home

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


    init{
        loadFirstPage()
    }

    private fun loadFirstPage(){

        _status.value = HomePokedexStatus.LOADING

        PokeApi.retrofitService.getPokes(page).enqueue(object : Callback<PokeRootProperty> { override fun onFailure(call: Call<PokeRootProperty>, t: Throwable) {
                    _status.value = HomePokedexStatus.ERROR
                }
                override fun onResponse(call: Call<PokeRootProperty>, response: Response<PokeRootProperty>) {
                    val itemsResponse = response.body()

                    _poke.value  = itemsResponse?.results!!

                    if(_poke.value.isNullOrEmpty()){
                        _status.value = HomePokedexStatus.EMPTY
                    }else{
                        val r = Runnable {
                            _status.value = HomePokedexStatus.DONE
                        }
                        Handler().postDelayed(r, 500)

                    }

                }
            })
    }

    fun getPage(page: Int){
        isLoading = true
        _status.value = HomePokedexStatus.LOADING

        PokeApi.retrofitService.getPokes(page)
            .enqueue(object : Callback<PokeRootProperty> {
                override fun onFailure(call: Call<PokeRootProperty>, t: Throwable) {
                    _status.value = HomePokedexStatus.ERROR
                }

                override fun onResponse(
                    call: Call<PokeRootProperty>,
                    response: Response<PokeRootProperty>
                ) {

                    val length = response.body()?.results?.size

                    for (i in 0 until length!!) {
                        _poke.value?.add((response.body()?.results!![i]))
                    }

                    isLoading = false
                    val r = Runnable {
                        _status.value = HomePokedexStatus.DONE
                    }
                    Handler().postDelayed(r, 500)
                }
            })
    }

    fun onRecyclerViewScrolled(dy: Int, layoutManager: GridLayoutManager){
        if(dy>0){
            val isItTheListEnd = itIsTheListEnd(layoutManager = layoutManager)
            if(isLoading.not() && isItTheListEnd){
                page+=20
                getPage(page)
            }
        }
    }

    fun itIsTheListEnd(layoutManager: GridLayoutManager) : Boolean{
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        return visibleItemCount + firstVisibleItemPosition >= totalItemCount
    }
}