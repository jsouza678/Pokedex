package souza.home.com.pokedexapp.domain.usecase

import souza.home.com.pokedexapp.domain.repository.SearchRepository

class SearchPokesByName(private val searchRepository: SearchRepository) {
    operator fun invoke(name: String) = searchRepository.searchPokesByName(name)
}
