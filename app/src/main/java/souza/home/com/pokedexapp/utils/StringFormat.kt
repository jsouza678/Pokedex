package souza.home.com.pokedexapp.utils

import souza.home.com.pokedexapp.data.pokedex.remote.model.response.EvolutionChainResponse
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.FlavorDescription
import souza.home.com.pokedexapp.utils.Constants.Companion.ABSOLUTE_ZERO
import souza.home.com.pokedexapp.utils.Constants.Companion.EMPTY_STRING
import souza.home.com.pokedexapp.utils.Constants.Companion.LANGUAGE_DESCRIPTIONS

fun cropPokeUrl(url: String): String {
    val id = url.substringAfterLast("n/").substringBeforeLast("/")
    return id
}

fun cropAbilityUrl(url: String): String {
    val id = url.substringAfterLast("y/").substringBeforeLast("/")
    return id
}

fun cropTypeUrl(url: String): String {
    val id = url.substringAfterLast("e/").substringBeforeLast("/")
    return id
}

fun optimizeDescription(it: MutableList<FlavorDescription>?): String {
    var description: String = EMPTY_STRING

    for (i in ABSOLUTE_ZERO until it?.size!!) {
        if (it.get(i).language.name == LANGUAGE_DESCRIPTIONS) {
            description += it[i].flavor_text + "\n "
        }
    }
    return description
}

fun optimizeChain(it: EvolutionChainResponse): MutableList<String> {
    val croppedList = mutableListOf<String>()

    it.chain.species?.name.let { it1 ->
        if (it1 != null) {
            croppedList.add(it1)
        }
    }
    it.chain.evolves_to?.get(0)?.species?.name.let { it1 ->
        if (it1 != null) {
            croppedList.add(it1)
        }
    }
    it.chain.evolves_to?.get(0)?.evolves_to?.get(0)?.species?.name.let { it1 ->
        if (it1 != null) {
            croppedList.add(it1)
        }
    }

    return croppedList
}

fun isString(text: String): Boolean {
    var numeric = true
    numeric = text.matches("-?\\d+(\\.\\d+)?".toRegex())

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
