package souza.home.com.pokedexapp.network.model.evolution_chain

import com.squareup.moshi.Json

data class PokeEvolvesTo(
    @Json(name="evolves_to")
    var evolution: PokeEvolution
)