package souza.home.com.pokedexapp.data.pokedex.remote.model.evolutionchain

import com.squareup.moshi.Json

data class Evolution(
    @Json(name = "evolves_to")
    val evolvesTo: List<Evolution>?,
    val species: Species?
)
