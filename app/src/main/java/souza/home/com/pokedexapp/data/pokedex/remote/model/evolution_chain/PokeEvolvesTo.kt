package souza.home.com.pokedexapp.data.pokedex.remote.model.evolution_chain

import com.squareup.moshi.Json

data class PokeEvolvesTo(
    @Json(name="evolves_to")
    var evolution: PokeEvolution
)