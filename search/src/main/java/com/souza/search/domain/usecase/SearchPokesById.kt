package com.souza.search.domain.usecase

import com.souza.search.domain.repository.SearchRepository

class SearchPokesById(private val searchRepository: SearchRepository) {
    operator fun invoke(id: Int) = searchRepository.searchPokesById(id)
}
