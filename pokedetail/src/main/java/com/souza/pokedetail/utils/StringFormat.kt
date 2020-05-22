package com.souza.pokedetail.utils

import com.souza.pokedetail.data.pokedex.remote.model.variety.FlavorDescription
import com.souza.pokedetail.data.pokedex.remote.response.EvolutionChainResponse
import com.souza.pokedetail.utils.Constants.Companion.ABSOLUTE_ZERO
import com.souza.pokedetail.utils.Constants.Companion.EMPTY_STRING
import com.souza.pokedetail.utils.Constants.Companion.LANGUAGE_DESCRIPTIONS

fun cropPokeUrl(url: String): String {
    return url.substringAfterLast("n/").substringBeforeLast("/")
}

fun cropAbilityUrl(url: String): String {
    return url.substringAfterLast("y/").substringBeforeLast("/")
}

fun cropTypeUrl(url: String): String {
    return url.substringAfterLast("e/").substringBeforeLast("/")
}

fun optimizeDescription(it: MutableList<FlavorDescription>?): String {
    var description: String = EMPTY_STRING

    for (i in ABSOLUTE_ZERO until it?.size!!) {
        if (it[i].language?.name == LANGUAGE_DESCRIPTIONS) {
            description += it[i].flavorText + "\n "
        }
    }
    return description
}

fun optimizeChain(item: EvolutionChainResponse): MutableList<String> {
    val evolutionArray = mutableListOf<String>()

    if (item.chain?.species?.name != null) {
        evolutionArray.add(item.chain.species.name)
        try { // 2 If poke has the first evolution
            evolutionArray.add(item.chain.evolvesTo!![0].species?.name!!)
            try { // 3 If poke has the second evolution
                evolutionArray.add(item.chain.evolvesTo[0].evolvesTo!![0].species?.name!!)
            } catch (e: Exception) { }
        } catch (e: Exception) { }
    }
    return evolutionArray
}

fun isString(text: String): Boolean {
    val numeric: Boolean = text.matches("-?\\d+(\\.\\d+)?".toRegex())

    if (text == EMPTY_STRING) {
        return false
    }

    if (numeric) {
        return try {
            Integer.parseInt(text)
            true
        } catch (e: Exception) {
            false
        }
    }
    return numeric
}
