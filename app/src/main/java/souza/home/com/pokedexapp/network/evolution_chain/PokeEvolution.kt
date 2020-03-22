package souza.home.com.pokedexapp.network.evolution_chain

data class PokeEvolution(
    var evolves_to: List<PokeEvolution>,
    var species: PokeSpecies
)