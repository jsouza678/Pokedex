package souza.home.com.pokedexapp.ui.home

import android.os.Handler
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import souza.home.com.pokedexapp.network.PokeApi
import souza.home.com.pokedexapp.network.PokeRootProperty
import souza.home.com.pokedexapp.network.model.main_model.Pokemon
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData

enum class HomePokedexStatus{ LOADING, ERROR, DONE, EMPTY}

class HomePokedexViewModel : ViewModel(){

    var isLoading = false

    private var page = 0

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

    fun getPage(page: Int) : MutableList<Pokemon>{
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

                  /*  if(length!! > 0){
                        _status.value = HomePokedexStatus.EMPTY
                    }else{

                    }*/

                    val r = Runnable {
                        _status.value = HomePokedexStatus.DONE
                        isLoading = false
                    }
                    Handler().postDelayed(r, 500)


                }

            })
        return _poke.value!!
    }


}