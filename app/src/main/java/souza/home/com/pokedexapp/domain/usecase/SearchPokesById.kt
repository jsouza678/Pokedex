package souza.home.com.pokedexapp.domain.usecase

import souza.home.com.pokedexapp.domain.repository.SearchRepository

class SearchPokesById (private val searchRepository: SearchRepository) {
    operator fun invoke(id: Int) = searchRepository.searchPokesById(id)
}
