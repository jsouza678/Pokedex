package souza.home.com.pokedexapp.network.varieties

data class PokeRootVarieties(
    var evolution_chain: PokeEvolutionPath,
    var varieties: List<PokeVarieties>
)