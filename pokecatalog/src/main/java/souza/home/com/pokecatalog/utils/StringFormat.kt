package souza.home.com.pokecatalog.utils

import souza.home.com.pokecatalog.utils.Constants.Companion.EMPTY_STRING

fun cropPokeUrl(url: String): String {
    return url.substringAfterLast("n/").substringBeforeLast("/")
}

fun cropAbilityUrl(url: String): String {
    return url.substringAfterLast("y/").substringBeforeLast("/")
}

fun cropTypeUrl(url: String): String {
    return url.substringAfterLast("e/").substringBeforeLast("/")
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
