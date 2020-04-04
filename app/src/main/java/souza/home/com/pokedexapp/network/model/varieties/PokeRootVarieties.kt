package souza.home.com.pokedexapp.network.model.varieties

import com.squareup.moshi.Json

data class PokeRootVarieties(
    var evolution_chain: PokeEvolutionPath,
    var varieties: MutableList<PokeVarieties>,
    var color: PokeColor,
    @Json(name="flavor_text_entries")
    var description: MutableList<PokeFlavorDescription>
)