package com.souza.search.utils

import com.souza.pokedetail.data.pokedex.remote.model.variety.FlavorDescription
import com.souza.search.utils.Constants.Companion.ABSOLUTE_ZERO
import com.souza.search.utils.Constants.Companion.EMPTY_STRING
import com.souza.search.utils.Constants.Companion.LANGUAGE_DESCRIPTIONS

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
