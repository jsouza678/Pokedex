package com.souza.search.data.mapper

import com.souza.pokecatalog.data.pokedex.remote.model.PokeRootResponse
import com.souza.search.utils.cropPokeUrl

class PokedexMapper {

    companion object {

        fun pokemonResponseAsDatabaseModel(pokeRootProperty: PokeRootResponse): Array<com.souza.pokecatalog.data.pokedex.local.entities.PokemonEntity>? {
            return pokeRootProperty.results?.map {
                com.souza.pokecatalog.data.pokedex.local.entities.PokemonEntity(
                    _id = Integer.parseInt(cropPokeUrl(it.id)),
                    name = it.name
                )
            }?.toTypedArray()
        }

        fun pokemonEntityAsDomainModel(pokemonEntity: List<com.souza.pokecatalog.data.pokedex.local.entities.PokemonEntity>?): List<com.souza.pokecatalog.domain.model.Pokemon>? {
            return pokemonEntity?.map {
                com.souza.pokecatalog.domain.model.Pokemon(
                    id = it._id,
                    name = it.name
                )
            }
        }
    }
}
