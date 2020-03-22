package souza.home.com.pokedexapp.network.evolution_chain

import com.squareup.moshi.Json

data class PokeEvolvesTo(
    @Json(name="evolves_to")
    var evolution: PokeEvolution
)