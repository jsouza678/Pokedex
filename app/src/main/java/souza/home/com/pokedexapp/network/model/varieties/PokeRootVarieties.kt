package souza.home.com.pokedexapp.network.model.varieties

import com.squareup.moshi.Json

data class PokeRootVarieties(
    var id: String = "",
    var evolution_chain: PokeEvolutionPath,
    var varieties: MutableList<PokeVarieties>,
    var habitat: PokeHabitat,
    var color: PokeColor,
    @Json(name="flavor_text_entries")
    var description: MutableList<PokeFlavorDescription>
)