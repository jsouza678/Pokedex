package souza.home.com.pokedexapp.data.pokedex.remote.model.evolution_chain

data class Evolution(
    var evolves_to: List<Evolution>?,
    var species: Species?
)