package souza.home.com.pokecatalog.data.pokedex.remote

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query
import souza.home.com.pokecatalog.data.pokedex.remote.model.PokeRootResponse

interface PokeCatalogService {

    @GET("pokemon/?")
    fun fetchPokesAsync(@Query("offset") page: Int?):
            Deferred<PokeRootResponse> // Cached
}
