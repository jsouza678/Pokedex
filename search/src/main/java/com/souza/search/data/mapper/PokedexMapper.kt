package com.souza.search.data.mapper

import com.souza.pokecatalog.data.pokedex.local.entities.PokemonEntity
import com.souza.pokecatalog.data.pokedex.remote.model.PokeRootResponse
import com.souza.pokecatalog.domain.model.Pokemon
import com.souza.search.utils.cropPokeUrl

class PokedexMapper {

    companion object {
        fun pokemonResponseAsDatabaseModel(pokeRootProperty: PokeRootResponse): Array<PokemonEntity>? {
            return pokeRootProperty.results?.map {
                PokemonEntity(
                    _id = Integer.parseInt(cropPokeUrl(it.id)),
                    name = it.name
                )
            }?.toTypedArray()
        }

        fun pokemonEntityAsDomainModel(pokemonEntity: List<PokemonEntity>?): List<Pokemon>? {
            return pokemonEntity?.map {
                Pokemon(
                    id = it._id,
                    name = it.name
                )
            }
        }
    }
}
