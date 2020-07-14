package com.souza.search.domain.usecase

import com.souza.search.domain.repository.SearchRepository

class SearchPokesByName(private val searchRepository: SearchRepository) {

    operator fun invoke(name: String) = searchRepository.searchPokesByName(name)
}
